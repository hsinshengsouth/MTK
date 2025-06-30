package idv.po.mtk_src.infrastructure.filter;

import idv.po.mtk_src.infrastructure.redis.RedisService;
import idv.po.mtk_src.infrastructure.utils.JwtUtils;
import idv.po.mtk_src.management.domain.user.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtUtils jwtUtils;
  private final CustomUserDetailsService userDetailsService;
  private final RedisService redisService;

  private static final List<String> PUBLIC_PATHS =
      List.of(
          "/auth/member/login",
          "/auth/member/register",
          "/auth/member/logout",
          "/auth/admin/login",
          "/auth/admin/register",
          "/auth/admin/logout");

  private final AntPathMatcher antPathMatcher = new AntPathMatcher();

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {

    String path = request.getServletPath();

    boolean isPublic = PUBLIC_PATHS.stream().anyMatch(p -> antPathMatcher.match(p, path));

    if (isPublic) {
      filterChain.doFilter(request, response);
      return;
    }

    final String authHeader = request.getHeader("Authorization");
    String jwtToken = null;
    String userEmail = null;

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }
    jwtToken = authHeader.substring(7);
    userEmail = jwtUtils.getUserName(jwtToken);

    if (!redisService.isValidToken(jwtToken)) {
      filterChain.doFilter(request, response);
      return;
    }

    if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

      List<String> roles = jwtUtils.getRoles(jwtToken);

      List<GrantedAuthority> authorities =
          roles.stream()
              .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role)
              .map(SimpleGrantedAuthority::new)
              .collect(Collectors.toList());

      UserDetails user = userDetailsService.loadUserByUsername(userEmail);
      if (jwtUtils.validateToken(jwtToken, user)) {
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(user, null, authorities);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }
    filterChain.doFilter(request, response);
  }
}
