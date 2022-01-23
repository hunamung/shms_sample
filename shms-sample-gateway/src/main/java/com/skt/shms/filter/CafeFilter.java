package com.skt.shms.filter;

import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CafeFilter extends AbstractGatewayFilterFactory<CafeFilter.Config> {
    private static final Logger logger = LogManager.getLogger(CafeFilter.class);
    public CafeFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        
        GatewayFilter filter = new OrderedGatewayFilter((exchange, chain) -> {
            //WebFlux를 활용하여 비동기 처리에서 request와 response를 가져올 수 있다.
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            logger.info("CafeFilter PRE filter : request id -> {}", request.getId());

            //post filter 동작
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                //스프링5에서 지원하는 기능으로 비동기로 값을 전달할때 사용되는 객체
                logger.info("CafeFilter POST filter : response id -> {}", response.getStatusCode());
            }));
        }, Ordered.HIGHEST_PRECEDENCE); //필터의 우선순위
        return filter;
    }

    @Data
    public static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }
}