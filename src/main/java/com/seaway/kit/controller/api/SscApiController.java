package com.seaway.kit.controller.api;

import com.seaway.kit.pojo.ssc.AwardInfo;
import com.seaway.kit.service.SscApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/kit/ssc")
public class SscApiController {

    private final SscApiService sscApiService;

    public SscApiController(SscApiService sscApiService) {
        this.sscApiService = sscApiService;
    }

    @GetMapping("/latest")
    public Mono<AwardInfo> getLatest() {
        return sscApiService.getLatest();
    }

    @GetMapping("/history")
    public Flux<AwardInfo> getHistory(@RequestParam(value = "rid") final String rid) {
        return sscApiService.getHistory(rid);
    }

}
