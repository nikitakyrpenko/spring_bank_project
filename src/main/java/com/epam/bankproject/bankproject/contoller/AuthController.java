package com.epam.bankproject.bankproject.contoller;
import com.epam.bankproject.bankproject.domain.User;
import com.epam.bankproject.bankproject.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Controller
public class AuthController {

    private final UserService userService;

    @GetMapping(value = "/register")
    public ModelAndView showRegistrationPage(ModelAndView modelAndView, User user) {
        modelAndView.addObject("user", user);
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @PostMapping(value = "/register")
    public ModelAndView processRegistrationForm(ModelAndView modelAndView,
                                                @Valid User user) {
        userService.register(user);
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @GetMapping(value = "/login/error")
    public ModelAndView showLoginErrorPage(ModelAndView modelAndView){
        modelAndView.setViewName("login-error.html");
        return modelAndView;
    }

}
