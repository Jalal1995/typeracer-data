package com.example.crawler.controller;

import com.example.crawler.exception.NoDataFoundException;
import com.example.crawler.entity.Race;
import com.example.crawler.service.TypeRacerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TypeRacerController {

    private final TypeRacerService typeRacerService;

    @GetMapping("/races")
    public List<Race> handleRace(@RequestParam String email,
                                 @RequestParam(required = false) String from,
                                 @RequestParam(required = false) String to) {
        List<Race> list =  typeRacerService.races(email, from, to)
                .orElseThrow(() -> new NoDataFoundException("no data found"));
        if (list.size() == 0) throw new NoDataFoundException("no data found");
        return list;
    }
}
