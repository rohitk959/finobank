package com.finobank.users.core.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class OAuth2TokenFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest httpRequest) {
            JwtAuthenticationToken authentication = (JwtAuthenticationToken) httpRequest.getUserPrincipal();
            if (authentication != null) {
                Jwt jwt = (Jwt) authentication.getCredentials();
                if (jwt != null) {
                    String userId = jwt.getSubject();
                    TokenMetaData.setUserId(UUID.fromString(userId));

                    String username = (String) jwt.getClaims().get("preferred_username");
                    TokenMetaData.setUsername(username);
                }
            }
        }

        try {
            chain.doFilter(request, response);
        } finally {
            TokenMetaData.clear();
        }
    }
}
