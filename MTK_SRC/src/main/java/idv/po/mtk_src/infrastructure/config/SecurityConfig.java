package idv.po.mtk_src.infrastructure.config;


import idv.po.mtk_src.infrastructure.filter.JwtAuthenticationFilter;
import idv.po.mtk_src.infrastructure.utils.JwtUtils;
import idv.po.mtk_src.management.domain.user.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtils jwtUtils;
    private final  JwtAuthenticationFilter jwtAuthenticationFilter;

    private final CustomUserDetailsService customUserDetailsService;


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    @Order(1)
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) throws Exception {
       return  http
               .securityMatcher("/auth/admin/**","movies/command")
               .csrf(AbstractHttpConfigurer::disable)
               .authorizeHttpRequests(
                       auth -> auth
                       .requestMatchers(
                               "/auth/admin/register",
                               "/auth/admin/login",
                               "/auth/admin/logout"
                       ).permitAll()
                       .requestMatchers("/movies/command").hasAnyRole("ADMIN", "USER")
                       .anyRequest().authenticated()
               )
               .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               .authenticationProvider(authenticationProvider())
               .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
               .build();

    }


    @Bean
    @Order(1)
    public SecurityFilterChain memberSecurityFilterChain(HttpSecurity http) throws Exception {
        return  http
                .securityMatcher("/auth/member/**")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/member/register",
                                "/auth/member/login",
                                "/auth/member/logout").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }



    @Bean
    @Order(3)
    public SecurityFilterChain publicQueryChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/movies/query/**")
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }

    // 4. 其它路徑，預設不允許
    @Bean
    @Order(4)
    public SecurityFilterChain defaultChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth.anyRequest().denyAll());
        return http.build();
    }


}
