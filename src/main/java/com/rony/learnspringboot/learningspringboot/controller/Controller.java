package com.rony.learnspringboot.learningspringboot.controller;

import com.rony.learnspringboot.learningspringboot.model.Image;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log
@RestController
public class Controller {

    private static final String API_BASE_BATH = "/api";

    @GetMapping(API_BASE_BATH + "/images")
    Flux<Image> images() {
        return Flux.just(
                new Image("1", "learnSpring.jpg"),
                new Image("2", "learnSpringBoot2.jpg"),
                new Image("3", "hello.jpg")
        );
    }

    @PostMapping(API_BASE_BATH + "/images")
    Mono<Void> create(@RequestBody Flux<Image> images) {
        return images
                .map(
                        image -> {
                            log.info("We will save " + image + " to a Reactive database soon!");
                            return image;
                        }).then();
    }
}
