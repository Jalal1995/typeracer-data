package com.example.crawler.controller;

import com.example.crawler.service.ChartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class ChartController {

    private final ChartService chartService;

    @GetMapping("chart")
    public ModelAndView getChart(@RequestParam String email,
                                 @RequestParam(required = false) String from,
                                 @RequestParam(required = false) String to,
                                 ModelAndView mav) {
        String url = chartService.createUrl(email, from, to);
        mav.setViewName("chart");
        mav.addObject("url", url);
        return mav;
    }
}
