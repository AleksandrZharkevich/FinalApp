package by.mrbregovich.iba.project.controller;

import by.mrbregovich.iba.project.dto.MailDto;
import by.mrbregovich.iba.project.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class MailController {

    private final MailService mailService;

    @Autowired
    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @GetMapping("/contact")
    public ModelAndView contact() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("mailDto", new MailDto());
        modelAndView.setViewName("contact");
        return modelAndView;
    }

    @PostMapping("/contact")
    public ModelAndView sendMail(@Valid @ModelAttribute("mailDto") MailDto mailDto, Errors errors) {
        ModelAndView modelAndView = new ModelAndView();
        if (errors.hasErrors()) {
            modelAndView.setViewName("contact");
        } else {
            mailService.sendEmail(mailDto);
            modelAndView.setViewName("redirect:/");
        }
        return modelAndView;
    }
}
