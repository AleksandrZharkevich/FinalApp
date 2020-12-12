package by.mrbregovich.iba.project.controller;

import by.mrbregovich.iba.project.constants.AppConstants;
import by.mrbregovich.iba.project.dto.ContactDto;
import by.mrbregovich.iba.project.entity.Request;
import by.mrbregovich.iba.project.entity.RequestStatus;
import by.mrbregovich.iba.project.entity.User;
import by.mrbregovich.iba.project.exception.RequestAlreadyRegistered;
import by.mrbregovich.iba.project.exception.RequestNotFoundException;
import by.mrbregovich.iba.project.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("/request/take/{id}")
    public ModelAndView takeRequest(@PathVariable("id") Long requestId, @AuthenticationPrincipal User user) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            Request request = requestService.findById(requestId);

            if (!request.getCompany().getParticipants().contains(user) && !request.getCompany().getOwner().equals(user)) {
                modelAndView.addObject("errorMessage", AppConstants.USER_IS_NOT_PARTICIPANT);
            } else {
                request.setRequestStatus(RequestStatus.IN_PROCESS);
                request.setManager(user);
                requestService.save(request);
            }
            modelAndView.setViewName("forward:/companies/" + request.getCompany().getId());

        } catch (RequestNotFoundException e) {
            e.printStackTrace();
            modelAndView.setViewName("redirect:/");
        }
        return modelAndView;
    }

    @GetMapping("/request/process/{id}")
    public ModelAndView closeRequest(@PathVariable("id") Long requestId, @AuthenticationPrincipal User user) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            Request request = requestService.findById(requestId);

            if (request.getManager().equals(user)) {
                requestService.closeRequest(request);
                modelAndView.setViewName("forward:/profile");
            } else {
                modelAndView.setViewName("redirect:/");
            }
        } catch (RequestNotFoundException e) {
            e.printStackTrace();
            modelAndView.setViewName("redirect:/");
        }
        return modelAndView;
    }

    @GetMapping("/request/return/{id}")
    public ModelAndView returnRequest(@PathVariable("id") Long requestId, @AuthenticationPrincipal User user) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            Request request = requestService.findById(requestId);

            if (request.getManager().equals(user)) {
                requestService.returnRequest(request);
                modelAndView.setViewName("forward:/profile");
            } else {
                modelAndView.setViewName("redirect:/");
            }
        } catch (RequestNotFoundException e) {
            e.printStackTrace();
            modelAndView.setViewName("redirect:/");
        }
        return modelAndView;
    }
}
