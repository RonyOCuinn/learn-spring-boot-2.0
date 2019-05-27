package com.ronyo.learnspringboot.learningspringboot.configuration;

import com.ronyo.learnspringboot.learningspringboot.data.Chapter;
import com.ronyo.learnspringboot.learningspringboot.repository.ChapterRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner init(ChapterRepository repository) {
        return args -> Flux.just(
                new Chapter("Quick start with Java"),
                new Chapter("Reactive Web with Spring Boot"),
                new Chapter("... and more!"))
                .flatMap(repository::save)
                .subscribe(System.out::println);
    }

}
