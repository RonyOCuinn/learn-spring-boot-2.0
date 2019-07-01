package com.rony.learnspringboot.learningspringboot.repository;

import com.rony.learnspringboot.learningspringboot.model.Image;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ImageRepository extends ReactiveCrudRepository<Image, String> {

    Mono<Image> findByName(String name);

}
