package com.rony.learnspringboot.learningspringboot.model;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ImageTests {

    @Test
    public void imagesManagedByLombokShouldWork() {
        Image image = new Image("id", "file-name.jpg");
        assertThat(image.getId()).isEqualTo("id");
        assertThat(image.getName()).isEqualTo("file-name.jpg");
    }

}