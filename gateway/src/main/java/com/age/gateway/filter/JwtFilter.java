package com.age.gateway.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
public class JwtFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        String authorization = headers.getFirst("authorization");
        String url = request.getPath().value();
        System.out.println("url = "+url);
        if (url.startsWith("/index/") || url.startsWith("/user/sso/")){
            return chain.filter(exchange);
        }
        if (StringUtils.isBlank(authorization)){
            String longToken = headers.getFirst("long-token");
            String currentToken = headers.getFirst("current-token");
            if (StringUtils.isBlank(longToken) || StringUtils.isBlank(currentToken)){
                //返回未登录，请进行登录
                return loginError(exchange);
            }

        }
        return chain.filter(exchange);
    }


    private Mono<Void> unauthorized(ServerWebExchange serverWebExchange) {
        serverWebExchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        DataBuffer buffer = serverWebExchange.getResponse()
                .bufferFactory().wrap(HttpStatus.UNAUTHORIZED.getReasonPhrase().getBytes());
        return serverWebExchange.getResponse().writeWith(Flux.just(buffer));
    }

    private Mono<Void> loginError(ServerWebExchange exchange){
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        DataBuffer buffer = exchange.getResponse()
                .bufferFactory().wrap("请先登录".getBytes());
        return exchange.getResponse().writeWith(Flux.just(buffer));
    }
}
