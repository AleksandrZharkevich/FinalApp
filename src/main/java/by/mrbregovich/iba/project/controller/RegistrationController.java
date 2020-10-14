package by.mrbregovich.iba.project.controller;

import by.mrbregovich.iba.project.dto.NewUserDto;
import by.mrbregovich.iba.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegistrationController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public ModelAndView registerForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("regForm", new NewUserDto()).setViewName("register");
        return modelAndView;
    }


//    @PostMapping
//    public String processRegistration(NewUserDto form){
//        //userRepository.save(form)
//    }
}
