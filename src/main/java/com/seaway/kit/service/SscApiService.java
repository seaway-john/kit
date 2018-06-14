package com.seaway.kit.service;

import com.seaway.kit.pojo.ssc.AwardInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SscApiService {

    private final WebClient webClient;

    public SscApiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://www.shengyuncai.cn").build();
    }

    public Mono<AwardInfo> getLatest() {
        return webClient.get().uri("/game-app/api/lobby/award/latest").retrieve().bodyToMono(AwardInfo.class);
    }

    public Flux<AwardInfo> getHistory(String rid) {
        return webClient.get().uri("/game-app/api/lobby/award/history?rid={rid}", rid).retrieve().bodyToFlux(AwardInfo.class);
    }

}
