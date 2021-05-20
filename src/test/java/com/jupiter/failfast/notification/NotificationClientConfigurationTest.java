package com.jupiter.failfast.notification;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = NotificationClientConfiguration.class)
@TestPropertySource(value = "classpath:application-test.yml")
@ContextConfiguration(
        initializers= {
               /* ConfigFileApplicationContextInitializer.class,*/
                ConfigDataApplicationContextInitializer.class
        }
)
class NotificationClientConfigurationTest {

    private static Validator propertyValidator;

    @Autowired
    private NotificationClientConfiguration configuration;

    @BeforeAll
    public static void setup() {
        propertyValidator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void baseUrl_shouldNotBeEmpty() {
        String oldValue = configuration.getBaseUrl();
        String expectedErrorMessage = "baseUrl property required and hasn't be empty";
        //give: set empty string to base url
        configuration.setBaseUrl("");

        //when: validate
        Set<ConstraintViolation<NotificationClientConfiguration>> actualValidatorResult =
                propertyValidator.validate(configuration);

        //then:
        assertThat(actualValidatorResult.size()).isEqualTo(1);
        assertThat(actualValidatorResult)
                .anyMatch(stringConstraintViolation ->
                        stringConstraintViolation.getMessage()
                                .equalsIgnoreCase(expectedErrorMessage));

        //then: set old value
        configuration.setBaseUrl(oldValue);
    }

    @Test
    public void baseUrl_shouldBeValid() {
        String oldValue = configuration.getBaseUrl();
        String expectedErrorMessage = "baseUrl property has to contain valid url";
        //give: set empty string to base url
        configuration.setBaseUrl("service");

        //when: validate
        Set<ConstraintViolation<NotificationClientConfiguration>> actualValidatorResult =
                propertyValidator.validate(configuration);

        //then:
        assertThat(actualValidatorResult.size()).isEqualTo(1);
        assertThat(actualValidatorResult)
                .anyMatch(stringConstraintViolation ->
                        stringConstraintViolation.getMessage()
                                .equalsIgnoreCase(expectedErrorMessage));

        //then: set old value
        configuration.setBaseUrl(oldValue);
    }

    @Test
    void resource_shouldNotBeEmpty() {
        String oldValue = configuration.getResource();
        String expectedErrorMessage = "must not be blank";
        //give: set empty string to base url
        configuration.setResource("");

        //when: validate
        Set<ConstraintViolation<NotificationClientConfiguration>> actualValidatorResult =
                propertyValidator.validate(configuration);

        //then:
        assertThat(actualValidatorResult.size()).isEqualTo(1);
        assertThat(actualValidatorResult)
                .anyMatch(stringConstraintViolation ->
                        stringConstraintViolation.getMessage()
                                .equalsIgnoreCase(expectedErrorMessage));

        //then: set old value
        configuration.setResource(oldValue);
    }


}