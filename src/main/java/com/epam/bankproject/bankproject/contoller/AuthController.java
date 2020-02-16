package com.epam.bankproject.bankproject.contoller;
import com.epam.bankproject.bankproject.domain.User;
import com.epam.bankproject.bankproject.service.UserService;
import lombok.AllArgsConstructor;
import org.h2.engine.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Controller
public class AuthController {

    private final UserService userService;

    @GetMapping(value = "/register")
    public ModelAndView showRegistrationPage(ModelAndView modelAndView, User user) {
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping(value = "/register")
    public ModelAndView processRegistrationForm(@Valid User user,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return new ModelAndView("registration");
        }

        userService.register(user);

        return new ModelAndView("redirect:/login");
    }

    @GetMapping(value = "/login/error")
    public ModelAndView showLoginErrorPage(ModelAndView modelAndView){
        modelAndView.setViewName("login-error.html");
        return modelAndView;
    }

   /* @GetMapping(value = "/international")
    public String getInternationalPage() {
        return "login";*/
}

