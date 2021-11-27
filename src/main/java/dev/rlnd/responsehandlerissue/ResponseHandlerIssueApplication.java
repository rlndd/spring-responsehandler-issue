package dev.rlnd.responsehandlerissue;

import java.time.Duration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@SpringBootApplication
public class ResponseHandlerIssueApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResponseHandlerIssueApplication.class, args);
    }

}

@Component
class DefaultNoCacheHeaderFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        var headers = exchange.getResponse().getHeaders();
        if (headers.getCacheControl() == null) {
            headers.setCacheControl(CacheControl.noCache());
        }
        return chain.filter(exchange);
    }
}

@RestController
class TestController {
    @GetMapping("/responseEntity")
    public Mono<ResponseEntity<String>> cached() {
        var rsp = ResponseEntity
                .status(HttpStatus.OK)
                .cacheControl(CacheControl.maxAge(Duration.ofHours(1)))
                .body("supposed to be cached");
        return Mono.just(rsp);
    }
}

@Configuration
class RouterConfig {
    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return route(GET("serverResponse"),
                req -> ServerResponse.ok()
                        .cacheControl(CacheControl.maxAge(Duration.ofHours(1)))
                        .body(BodyInserters.fromValue("supposed to be cached")
        ));
    }
}
