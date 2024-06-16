package com.finobank.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServerApplication.class, args);
    }

    @Bean
    public RouteLocator routeConfig(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(p -> p
                        .path("/finobank/accounts/**")
                        .filters(f -> f.rewritePath("/finobank/(?<segment>.*)", "/${segment}"))
                        .uri("lb://ACCOUNTS"))
                .route(p -> p
                        .path("/finobank/payments/**")
                        .filters(f -> f.rewritePath("/finobank/(?<segment>.*)", "/${segment}"))
                        .uri("lb://PAYMENTS"))
                .route(p -> p
                        .path("/finobank/users/**")
                        .filters(f -> f.rewritePath("/finobank/(?<segment>.*)", "/${segment}"))
                        .uri("lb://USERS"))
                .route(p -> p
                        .path("/docs/accounts/swagger-ui/**")
                        .filters(f -> f.rewritePath("/docs/accounts/swagger-ui/(?<segment>.*)", "/swagger-ui/${segment}"))
                        .uri("lb://ACCOUNTS"))
                .route(p -> p
                        .path("/docs/payments/swagger-ui/**")
                        .filters(f -> f.rewritePath("/docs/payments/swagger-ui/(?<segment>.*)", "/swagger-ui/${segment}"))
                        .uri("lb://PAYMENTS"))
                .route(p -> p
                        .path("/docs/users/swagger-ui/**")
                        .filters(f -> f.rewritePath("/docs/users/swagger-ui/(?<segment>.*)", "/swagger-ui/${segment}"))
                        .uri("lb://USERS"))
                .route(p -> p
                        .path("/docs/accounts/v3/api-docs/**")
                        .filters(f -> f.rewritePath("/docs/accounts/v3/api-docs/(?<segment>.*)", "/v3/api-docs/${segment}"))
                        .uri("lb://ACCOUNTS"))
                .route(p -> p
                        .path("/docs/payments/v3/api-docs/**")
                        .filters(f -> f.rewritePath("/docs/payments/v3/api-docs/(?<segment>.*)", "/v3/api-docs/${segment}"))
                        .uri("lb://PAYMENTS"))
                .route(p -> p
                        .path("/docs/users/v3/api-docs/**")
                        .filters(f -> f.rewritePath("/docs/users/v3/api-docs/(?<segment>.*)", "/v3/api-docs/${segment}"))
                        .uri("lb://USERS"))
                .build();

    }

}
