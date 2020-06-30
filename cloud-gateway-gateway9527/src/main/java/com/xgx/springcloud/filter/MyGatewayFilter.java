package com.xgx.springcloud.filter;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.HashMap;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.addOriginalRequestUrl;

/**
 * Description: <br/>
 * @author xgx<br/>
 * date: 2020/6/30 13:29<br/>
 */
@Slf4j
@Component
public class MyGatewayFilter implements GlobalFilter, Ordered {
    static HashMap<String, String> routeMap = new HashMap<>();

    static {
        routeMap.put("/ent/query", "1001");
    }
    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest req = exchange.getRequest();
        addOriginalRequestUrl(exchange, req.getURI());

        Route route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);
        URI uri = UriComponentsBuilder.fromUri( new URI(route.getUri()+ "/service/" + routeMap.get(req.getPath().toString()))).build().toUri();
        ServerHttpRequest request = req.mutate().uri(uri).build();

//        Route newRoute = Route.async()
//                .asyncPredicate(route.getPredicate())
//                .filters(route.getFilters())
//                .id(route.getId())
//                .order(route.getOrder())
//                .uri(uri).build();
//        exchange.getAttributes().put(GATEWAY_ROUTE_ATTR, newRoute);
        return chain.filter(exchange.mutate().request(request).build());
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
