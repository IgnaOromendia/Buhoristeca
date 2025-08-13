package com.itpatagonia.Buhoristeca.configuration;

import com.itpatagonia.Buhoristeca.services.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Configuration
public class SecurityConfig {

    @Value("${front.url}")
    private String frontUrl;

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityfilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> {
                    csrf.csrfTokenRepository(new HttpSessionCsrfTokenRepository());
                    csrf.ignoringRequestMatchers("/api/**");
                })
                .authorizeHttpRequests(authz ->
                            authz.requestMatchers("/api/auth/login", "/swagger-ui/**")
                                    .permitAll()
                                    .requestMatchers("/api/user/**")
                                    .hasAnyRole("USUARIO", "EMPLEADO")
                                    .anyRequest()
                                    .authenticated()
                )
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class).exceptionHandling(exception ->
                        exception.authenticationEntryPoint(((request, response, authException) -> {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, String.valueOf(UNAUTHORIZED));
                        })));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(frontUrl));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
