package com.endava.twitter.autentication;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BasicAuthClient {

    private final RestTemplate restTemplate;

    public BasicAuthClient(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("admin", "password"));

    }

    public void invokeProtectedResource() {
        final ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:8080/secured/hello", String.class);
        final String body = responseEntity.getBody();
        System.out.println("body = " + body);
    }

}
