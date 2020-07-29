package com.example.crawler.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ChartService {

    @Value("${url.heroku}")
    private String url;

    public String createUrl(String email, String from, String to) {
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        sb.append(email);
        if (from != null) {
            sb.append("&from=");
            sb.append(from);
        }
        if (to != null) {
            sb.append("&to=");
            sb.append(to);
        }
        return sb.toString();
    }
}
