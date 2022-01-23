package com.skt.shms.filter;

import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class UserFilter extends AbstractGatewayFilterFactory<UserFilter.Config> {
    private static final Logger logger = LogManager.getLogger(UserFilter.class);
    public UserFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();
            
            logger.info("UserFilter baseMessage>>>>>>" + config.getBaseMessage());
            
            if (config.isPreLogger()) {
                //logger.info("UserFilter Start>>>>>>" + exchange.getRequest());
                logger.info("UserFilter Start : request id -> {}", request.getId());
            }
            
            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                if (config.isPostLogger()) {
                    //logger.info("UserFilter End>>>>>>" + exchange.getResponse());
                    logger.info("UserFilter End : response id -> {}", response.getStatusCode());
                }
            }));
        });
    }

    @Data
    public static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }
}