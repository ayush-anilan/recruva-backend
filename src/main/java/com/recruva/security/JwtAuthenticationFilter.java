package com.recruva.security;

import java.io.IOException;
import java.util.UUID;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.recruva.db.repositories.UserRepository;
import com.recruva.service.JwtService;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor 
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final JwtService jwtService;
    private final UserRepository userRepository;

 
@Override
protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
) throws ServletException, IOException {

    String authHeader = request.getHeader("Authorization");

    // No Bearer token: let Spring Security handle authentication.
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        filterChain.doFilter(request, response);
        return;
    }

    String token = authHeader.substring(7).trim();

    // Reject empty or invalid tokens.
    if (token.isEmpty() || !jwtService.isTokenValid(token)) {
        SecurityContextHolder.clearContext();
        response.sendError(
            HttpServletResponse.SC_UNAUTHORIZED,
            "Invalid or expired JWT"
        );
        return;
    }

    try {
        UUID userId = jwtService.extractUserIdFromToken(token);

        var user = userRepository.findById(userId).orElse(null);

        if (user == null || !user.isActive()) {
            SecurityContextHolder.clearContext();
            response.sendError(
                HttpServletResponse.SC_UNAUTHORIZED,
                "User not found or inactive"
            );
            return;
        }

        if (SecurityContextHolder.getContext().getAuthentication() == null) {

            UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    user.getAuthorities()
                );

            authToken.setDetails(
                new WebAuthenticationDetailsSource()
                    .buildDetails(request)
            );

            SecurityContextHolder.getContext()
                .setAuthentication(authToken);
        }

    } catch (JwtException | IllegalArgumentException e) {
        SecurityContextHolder.clearContext();
        response.sendError(
            HttpServletResponse.SC_UNAUTHORIZED,
            "Invalid JWT"
        );
        return;
    }

    filterChain.doFilter(request, response);
}

}
