package com.racetracker.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtFilter implements Filter {

    @Autowired
    private JwtService jwtService;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String method = request.getMethod();
        String uri = request.getRequestURI();

        if (method.equalsIgnoreCase("GET")) {
            chain.doFilter(req, res);
            return;
        }

        if (uri.contains("/auth")) {
            chain.doFilter(req, res);
            return;
        }

        if ((method.equalsIgnoreCase("POST") || method.equalsIgnoreCase("DELETE"))) {
            String authHeader = request.getHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                if (!jwtService.isTokenValid(token)) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido.");
                    return;
                }
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Faltando token.");
                return;
            }
        }
        chain.doFilter(req, res);
    }


}
