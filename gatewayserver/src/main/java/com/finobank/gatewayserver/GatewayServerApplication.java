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
                        .filters(f -> f.rewritePath("/finobank/accounts/(?<segment>.*)", "/${segment}"))
                        .uri("lb://ACCOUNTS"))
                .route(p -> p
                        .path("/finobank/payments/**")
                        .filters(f -> f.rewritePath("/finobank/payments/(?<segment>.*)", "/${segment}"))
                        .uri("lb://PAYMENTS"))
                .route(p -> p
                        .path("/finobank/users/**")
                        .filters(f -> f.rewritePath("/finobank/users/(?<segment>.*)", "/${segment}"))
                        .uri("lb://USERS")).build();


    }

}
