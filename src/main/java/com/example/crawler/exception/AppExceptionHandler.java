package com.example.crawler.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Log4j2
public class AppExceptionHandler {

    @ExceptionHandler(NoDataFoundException.class)
    public ModelAndView handleUserNotFoundException(NoDataFoundException ex) {
        log.warn("exception caught {}", ex.getMessage());
        return new ModelAndView("not-found");
    }

}
