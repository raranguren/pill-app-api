package com.pillapp.api.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
public class SecurityConfiguration {

    @Value("${spring.h2.console.enabled:false}")
    private boolean isH2ConsoleEnabled;

    @Value("${management.endpoints.web.exposure.include:}")
    private String exposedActuatorsString;

    private final JwtTokenFilter jwtTokenFilter;

    public SecurityConfiguration(JwtTokenFilter filter) {
        this.jwtTokenFilter = filter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // for REST API to function, it needs CORS enabled and CSRF disabled
        // - CORS are cross-origin references, for example for ajax requests
        // - CSRF (cross-site request forgery) is not needed in a stateless API
        http = http.cors().and().csrf().disable();

        // Session management is STATELESS with a JWT token filter
        // When not provided, login with a username (defined by UserDetails)
        http = http.sessionManagement()
                .sessionCreationPolicy(STATELESS)
                .and().addFilterBefore(
                        jwtTokenFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        // Requests without proper authorization get a status 401
        http = http.exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, exception) -> response.sendError(
                                SC_UNAUTHORIZED,
                                exception.getMessage()
                        )
                ).and();

        // Allow access to the h2 console in dev environment
        if (isH2ConsoleEnabled) {
            http.authorizeRequests()
                    .antMatchers("/h2-console/**").permitAll();
            http.headers().frameOptions().disable();
        }

        // Allow access to actuators if defined in properties
        if (!exposedActuatorsString.isEmpty()) {
            http.authorizeRequests()
                    .antMatchers("/actuator/**").permitAll();
        }

        // Set permissions on endpoints
        http.authorizeRequests()
                // Endpoints that don't require authentication
                .antMatchers(POST, "/user").permitAll()
                .antMatchers(POST, "/login").permitAll()
                // Any other requests require authentication
                .anyRequest().authenticated();

        return http.build();
    }

    // Use BCrypt for password encryption
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // CORS configuration
    // Allows any cross-origin request
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

}
