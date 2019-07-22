package com.rony.learnspringboot.learningspringboot.repository;

import com.rony.learnspringboot.learningspringboot.model.Image;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataMongoTest
public class EmbeddedImageRepositoryTests {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    MongoOperations operations;

    @Before
    public void setUp() {
        operations.dropCollection(Image.class);
        operations.insert(new Image("1",
                "learning-spring-boot-cover.jpg"));
        operations.insert(new Image("2",
                "learning-spring-boot-2nd-edition-cover.jpg"));
        operations.insert(new Image("3",
                "pints.jpg"));
        operations.findAll(Image.class).forEach(image -> {
            System.out.println(image.toString());
        });

    }

    @Test
    public void findAllShouldWork() {
        Flux<Image> images = imageRepository.findAll();
        StepVerifier.create(images)
                .recordWith(ArrayList::new)
                .expectNextCount(3)
                .consumeRecordedWith(results -> {
                    assertThat(results).hasSize(3);
                    assertThat(results)
                            .extracting(Image::getName)
                            .contains(
                                    "learning-spring-boot-cover.jpg",
                                    "learning-spring-boot-2nd-edition-cover.jpg",
                                    "pints.jpg");
                })
                .expectComplete()
                .verify();
    }

    public void findByNameShouldWork() {
        Mono<Image> image = imageRepository.findByName("pints.jpg");
        StepVerifier.create(image)
                .expectNextMatches(results -> {
                    assertThat(results.getName()).isEqualTo("pints.jpg");
                    assertThat(results.getId()).isEqualTo(3);
                    return true;
                });
    }

}