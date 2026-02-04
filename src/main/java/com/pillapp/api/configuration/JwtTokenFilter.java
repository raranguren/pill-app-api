package com.pillapp.api.configuration;

import com.pillapp.api.service.JwtTokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.util.ObjectUtils.isEmpty;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;
    private final UserDetailsService userDetailsService;

    public JwtTokenFilter(JwtTokenService jwtTokenService,
                          UserDetailsService userDetailsService) {
        this.jwtTokenService = jwtTokenService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // Get authorization header and validate formatting
        var header = request.getHeader(AUTHORIZATION);
        if (isEmpty(header) || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // If header present, get JWT token and validate formatting
        var token = header.split(" ")[1].trim();
        if (!jwtTokenService.isValidToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // If the token is valid, find the user
        var username = jwtTokenService.getUsername(token);
        UserDetails userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            filterChain.doFilter(request, response);
            return;
        }

        // If the user exists, prepare authentication details for that user
        var authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, null);
        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request));

        // then Login
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // and callback to continue processing the filter chain
        filterChain.doFilter(request, response);

    }

}