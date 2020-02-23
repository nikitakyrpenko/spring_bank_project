package com.epam.bankproject.bankproject.contoller.util;

import com.epam.bankproject.bankproject.service.exception.MonetaryException;
import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

@Log4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(HttpServletRequest request, Exception ex) {
        log.info("Exception Occured:: URL=" + request.getRequestURL() + "Exception=" + ex);
        return "error";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleException(HttpServletRequest request, Exception ex) {
        log.info("Exception Occured:: URL=" + request.getRequestURL() + "Exception=" + ex);
        return "error";
    }
}
