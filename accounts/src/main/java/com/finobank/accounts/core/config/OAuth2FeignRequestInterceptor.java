package com.finobank.accounts.core.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class OAuth2FeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken jwtToken) {
            String tokenValue = jwtToken.getToken().getTokenValue();
            template.header("Authorization", "Bearer " + tokenValue);
        }
    }
}
