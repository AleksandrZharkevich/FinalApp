package by.mrbregovich.iba.project.controller;

import by.mrbregovich.iba.project.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class RequestController {

    private RequestService requestService;

    @Autowired
    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

//    public String getActiveRequests(Model model, @RequestParam("page") Optional<Integer> page){
//
//    }
}
