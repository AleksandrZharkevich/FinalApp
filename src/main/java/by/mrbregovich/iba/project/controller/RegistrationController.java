package by.mrbregovich.iba.project.controller;

import by.mrbregovich.iba.project.dto.NewUserDto;
import by.mrbregovich.iba.project.exception.UserAlreadyExistsException;
import by.mrbregovich.iba.project.repository.UserRepository;
import by.mrbregovich.iba.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public ModelAndView registerForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("regForm", new NewUserDto()).setViewName("register");
        return modelAndView;
    }


    @PostMapping("/register")
    public ModelAndView processRegistration(@Valid @ModelAttribute("regForm") NewUserDto form, Errors errors) {
        ModelAndView modelAndView = new ModelAndView();
        if (errors.hasErrors()) {
            modelAndView.setViewName("register");
        } else {
            try {
                userService.register(form);
                modelAndView.setViewName("login");
            } catch (UserAlreadyExistsException e) {
                modelAndView.addObject("errorMessage", e.getMessage());
                modelAndView.setViewName("register");
            }
        }
        return modelAndView;
    }
}
