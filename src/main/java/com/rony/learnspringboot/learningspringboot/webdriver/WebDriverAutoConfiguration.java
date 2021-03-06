package com.rony.learnspringboot.learningspringboot.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Bean;

@Configuration
@ConditionalOnClass
@EnableConfigurationProperties(WebDriverConfigurationProperties.class)
@Import({ChromeDriverFactory.class, FirefoxDriverFactory.class, SafariDriverFactory.class})
public class WebDriverAutoConfiguration {

    @Primary
    @Bean(destroyMethod = "quit")
    @ConditionalOnMissingBean(WebDriver.class)
    public WebDriver webDriver(
            FirefoxDriverFactory firefoxDriverFactory,
            SafariDriverFactory safariDriverFactory,
            ChromeDriverFactory chromeDriverFactory) {
        WebDriver driver = firefoxDriverFactory.getObject();
        if (driver == null) {
            driver = safariDriverFactory.getObject();
        }

        if (driver == null) {
            driver = chromeDriverFactory.getObject();
        }

        if (driver == null) {
            driver = new HtmlUnitDriver();
        }

        return driver;
    }

}
