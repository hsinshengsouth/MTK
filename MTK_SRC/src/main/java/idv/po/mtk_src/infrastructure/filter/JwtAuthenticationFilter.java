package idv.po.mtk_src.infrastructure.filter;

import idv.po.mtk_src.infrastructure.redis.RedisTokenService;
import idv.po.mtk_src.infrastructure.utils.JwtUtils;
import idv.po.mtk_src.management.domain.user.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService userDetailsService;
    private final RedisTokenService redisTokenService;

    @Override
    protected void doFilterInternal(
             @NonNull HttpServletRequest request,
             @NonNull HttpServletResponse response,
             @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader =request.getHeader("Authorization");
        String jwtToken = "";
        String userEmail= "";

        if(authHeader==null ||!authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        jwtToken = authHeader.substring(7);
        userEmail = jwtUtils.getUserName(jwtToken);

        if(!redisTokenService.isValidToken(jwtToken)){
            filterChain.doFilter(request, response);
            return;
        }


        if(userEmail!=null &&
                SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails user=userDetailsService.loadUserByUsername(userEmail);
            if (jwtUtils.validateToken(jwtToken, user)) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request,response);

    }
}
