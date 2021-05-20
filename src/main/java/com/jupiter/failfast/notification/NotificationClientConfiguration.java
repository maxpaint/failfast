package com.jupiter.failfast.notification;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Validated
@Data
@Configuration
@Accessors(chain = true)
@ConfigurationProperties(prefix = "notification")
public class NotificationClientConfiguration {

    @URL(message = "baseUrl property has to contain valid url")
    @NotBlank(message = "baseUrl property required and hasn't be empty")
    private String baseUrl;

    @NotNull(message = "port property required and hasn't be null")
    @Min(value = 0, message = "port property has to be bigger than 0")
    private Integer port;

    @NotBlank
    private String resource;

    public String getUserResource() {
        return baseUrl + ":" + port + "/" + resource;
    }
}
