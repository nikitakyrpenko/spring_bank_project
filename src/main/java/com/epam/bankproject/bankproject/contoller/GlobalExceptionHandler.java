package com.epam.bankproject.bankproject.contoller;

import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.servlet.http.HttpServletRequest;

@Log4j
@ControllerAdvice
public class GlobalExceptionHandler {

   /* @ExceptionHandler(IllegalArgumentException.class)
    public String handleException(HttpServletRequest request, Exception ex){
        log.info("Exception Occured:: URL="+request.getRequestURL() + "Exception="+ ex);
        System.out.println(ex.getMessage());
        return "error";
    }*/

}
