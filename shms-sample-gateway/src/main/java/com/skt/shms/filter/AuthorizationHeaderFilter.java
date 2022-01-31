package com.skt.shms.filter;

import lombok.Data;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.google.common.net.HttpHeaders;

import reactor.core.publisher.Mono;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {
    private static final Logger logger = LogManager.getLogger(AuthorizationHeaderFilter.class);
    public AuthorizationHeaderFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            logger.info("GlobalFilter baseMessage>>>>>>" + config.getBaseMessage());
            if (config.isPreLogger()) {
                logger.info("GlobalFilter Start>>>>>>" + exchange.getRequest());
            }
            
            ServerHttpRequest req = exchange.getRequest();
            
            if(!req.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "Not Found authorization headers!", HttpStatus.UNAUTHORIZED);
            }
            
            String authorization = Objects.requireNonNull(req.getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
            String token = authorization .replace("Bearer","").trim();
            if(!isJwtValid(token)) {
                return onError(exchange, "JWT token is not valid.", HttpStatus.UNAUTHORIZED);
            }
            
            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                if (config.isPostLogger()) {
                    logger.info("GlobalFilter End>>>>>>" + exchange.getResponse());
                }
            }));
        });
    }
    
    private Mono<Void> onError(ServerWebExchange exchange, String e, HttpStatus status){
        ServerHttpResponse res =  exchange.getResponse();
        res.setStatusCode(status);
        
        logger.error(e);
        return res.setComplete();
    }
    
    private boolean isJwtValid(String token) {
        boolean isValid = true;
        
        /*
        String subject = null;
        try {
            subject = Jwts.parser().setSigningKey(env.getProperty("token.secret"))
                    .parseClaimJws(token).getBody().getSubject();
        }catch(Exception ex) {
            isValid = false;
        }
        
        if(subject == null || subject.isEmpty()) {
            isValid = false;
        }
        */
        return isValid;
    }

    @Data
    public static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }
}