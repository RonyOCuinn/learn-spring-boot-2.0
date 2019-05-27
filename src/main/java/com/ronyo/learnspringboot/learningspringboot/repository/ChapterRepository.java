package com.ronyo.learnspringboot.learningspringboot.repository;

import com.ronyo.learnspringboot.learningspringboot.data.Chapter;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ChapterRepository extends ReactiveCrudRepository<Chapter, String> {



}
