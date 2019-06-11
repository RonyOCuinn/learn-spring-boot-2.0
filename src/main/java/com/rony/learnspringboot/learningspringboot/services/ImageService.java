package com.rony.learnspringboot.learningspringboot.services;

import com.rony.learnspringboot.learningspringboot.model.Image;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class ImageService {

    private static String UPLOAD_ROOT = "upload-dir";

    private final ResourceLoader resourceLoader;

    public ImageService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Bean
    CommandLineRunner setUp() throws IOException {
        return (args) -> {
            FileSystemUtils.deleteRecursively(new File(UPLOAD_ROOT));

            Files.createDirectory(Paths.get(UPLOAD_ROOT));

            FileCopyUtils.copy("Test File", new FileWriter(UPLOAD_ROOT + "/learning-spring-boot-cover.jpg"));

            FileCopyUtils.copy("Test File 2", new FileWriter(UPLOAD_ROOT + "/learning-spring-boot-cover_the_second.jpg"));

            FileCopyUtils.copy("Test File 3", new FileWriter(UPLOAD_ROOT + "/wdc.jpg"));
        };
    }

    public Flux<Image> findAllImages() {
        try{
            return Flux.fromIterable(
                    Files.newDirectoryStream(Paths.get(UPLOAD_ROOT)))
                    .map(path -> new Image(path.hashCode(), path.getFileName().toString()));
        } catch (IOException e) {
            return Flux.empty();
        }
    }

}
