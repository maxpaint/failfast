package com.jupiter.failfast.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.failfast.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@RequiredArgsConstructor
@Component
public class NotificationClient {

    private final NotificationClientConfiguration configuration;
    private final ObjectMapper mapper;


    public void notify(User user) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {

            HttpPost httpPost = new HttpPost(configuration.getUserResource());
            httpPost.setEntity(new StringEntity(mapper.writeValueAsString(user)));
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            client.execute(httpPost);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
