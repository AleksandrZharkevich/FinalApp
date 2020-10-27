package by.mrbregovich.iba.project.controller;

import by.mrbregovich.iba.project.dto.ContactDto;
import by.mrbregovich.iba.project.exception.RequestAlreadyRegistered;
import by.mrbregovich.iba.project.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class RequestController {

    private RequestService requestService;

    @Autowired
    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/request/{id}")
    public String requestForm(Model model, @PathVariable("id") Long id) {
        model.addAttribute("contactInfo", new ContactDto());
        model.addAttribute("companyId", id);
        return "addRequest";
    }

    @PostMapping("/request/{id}")
    public ModelAndView processRequest(@Valid @ModelAttribute("contactInfo") ContactDto contactDto,
                                       Errors errors, @PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        if (errors.hasErrors()) {
            modelAndView.addObject("companyId", id).setViewName("addRequest");
        } else {
            try {
                requestService.create(contactDto, id);
                modelAndView.setViewName("redirect:/companies/" + id);
            } catch (RequestAlreadyRegistered e) {
                modelAndView.addObject("errorMessage", e.getMessage());
                modelAndView.addObject("companyId", id).setViewName("addRequest");
            }
        }
        return modelAndView;
    }

//    public String getActiveRequests(Model model, @RequestParam("page") Optional<Integer> page){
//
//    }
}
