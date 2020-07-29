package com.example.crawler.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Beans {

    @Bean
    public RestTemplate buildRestTemplate() {
        return new RestTemplate();
    }
}
