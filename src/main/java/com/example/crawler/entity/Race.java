package com.example.crawler.entity;

import lombok.Value;

import java.time.LocalDate;

@Value
public class Race {

    public LocalDate date;
    public Integer wpm;
    public Double accuracy;

}
