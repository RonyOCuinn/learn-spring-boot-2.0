package com.rony.learnspringboot.learningspringboot.webdriver;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;

public class FirefoxDriverFactory implements ObjectFactory<FirefoxDriver> {
    private WebDriverConfigurationProperties properties;

    public FirefoxDriverFactory(WebDriverConfigurationProperties properties) {
        this.properties = properties;
    }

    public FirefoxDriver getObject() throws BeansException {
        if (properties.getFirefox().isEnabled()) {
            try {
                return new FirefoxDriver();
            } catch (WebDriverException e) {
                //gulp
            }
        }
        return null;
    }
}
