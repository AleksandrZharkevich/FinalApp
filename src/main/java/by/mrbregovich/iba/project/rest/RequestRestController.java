package by.mrbregovich.iba.project.rest;

import by.mrbregovich.iba.project.dto.RequestRestResponseDto;
import by.mrbregovich.iba.project.exception.UserNotFoundException;
import by.mrbregovich.iba.project.service.RequestService;
import by.mrbregovich.iba.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RequestRestController {

    private RequestService requestService;
    private UserService userService;

    @Autowired
    public RequestRestController(RequestService requestService, UserService userService) {
        this.requestService = requestService;
        this.userService = userService;
    }

    @GetMapping("/api/{companyId}/{pageNumber}")
    public List<RequestRestResponseDto> getActiveRequestsByPage(@PathVariable("companyId") Long companyId,
                                                                @PathVariable("pageNumber") Integer pageNumber) {
        return requestService.findRegisteredRequestsByCompanyIdAndPageNumber(companyId, pageNumber);
    }

}
