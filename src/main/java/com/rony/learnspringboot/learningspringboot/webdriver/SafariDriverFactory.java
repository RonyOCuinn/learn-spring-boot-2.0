package com.rony.learnspringboot.learningspringboot.webdriver;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;

public class SafariDriverFactory implements ObjectFactory<SafariDriver> {
    private WebDriverConfigurationProperties properties;

    public SafariDriverFactory(WebDriverConfigurationProperties properties) {
        this.properties = properties;
    }

    public SafariDriver getObject() throws BeansException {
        if (properties.getFirefox().isEnabled()) {
            try {
                return new SafariDriver();
            } catch (WebDriverException e) {
                //gulp
            }
        }
        return null;
    }
}

