package com.rony.learnspringboot.learningspringboot;

import com.rony.learnspringboot.learningspringboot.webdriver.FirefoxDriverFactory;
import com.rony.learnspringboot.learningspringboot.webdriver.WebDriverAutoConfiguration;
import org.apache.commons.lang3.ClassUtils;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class WebDriverAutoConfigurationTests {

    private AnnotationConfigApplicationContext context;

    @After
    public void close() {
        if (this.context != null) {
            this.context.close();
        }
    }

    private void load(Class<?>[] configs, String... environment) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();
        applicationContext
                .register(WebDriverAutoConfiguration.class);
        if (configs.length > 0) {
            applicationContext.register(configs);
        }
        TestPropertyValues.of(environment)
                .applyTo(applicationContext);
        applicationContext.refresh();
        this.context = applicationContext;
    }

    @Test
    public void fallbackToNonGuiModeWhenAllBrowsersDisabled() {
        load(new Class[]{},
                "com.rony.webdriver.firefox.enabled:false",
                "com.rony.webdriver.chrome.enabled:false",
                "com.rony.webdriver.safari.enabled:false"
                );
        WebDriver driver = context.getBean(WebDriver.class);
        assertThat(ClassUtils.isAssignable(TakesScreenshot.class, driver.getClass())).isFalse();
        assertThat(ClassUtils.isAssignable(HtmlUnitDriver.class, driver.getClass())).isTrue();
    }

    @Test
    public void testWithMockedFirefox() {
        load(new Class[]{MockFirefoxConfiguration.class},
                "com.rony.webdriver.safari.enabled:false",
                "com.rony.webdriver.chrome.enabled:false");
        WebDriver driver = context.getBean(WebDriver.class);
        assertThat(ClassUtils.isAssignable(TakesScreenshot.class,
                driver.getClass())).isTrue();
        assertThat(ClassUtils.isAssignable(FirefoxDriver.class,
                driver.getClass())).isTrue();
    }

    @Configuration
    protected static class MockFirefoxConfiguration {
        @Bean
        FirefoxDriverFactory firefoxDriverFactory() {
            FirefoxDriverFactory factory =
                    mock(FirefoxDriverFactory.class);
            given(factory.getObject())
                    .willReturn(mock(FirefoxDriver.class));
            return factory;
        }
    }

}
