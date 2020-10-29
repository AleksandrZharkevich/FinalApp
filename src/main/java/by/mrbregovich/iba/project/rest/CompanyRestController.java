package by.mrbregovich.iba.project.rest;

import by.mrbregovich.iba.project.constants.AppConstants;
import by.mrbregovich.iba.project.dto.CompanyResponseDto;
import by.mrbregovich.iba.project.dto.ResponseMessage;
import by.mrbregovich.iba.project.entity.Company;
import by.mrbregovich.iba.project.entity.User;
import by.mrbregovich.iba.project.exception.CompanyNotFoundException;
import by.mrbregovich.iba.project.exception.UserNotFoundException;
import by.mrbregovich.iba.project.service.CompanyService;
import by.mrbregovich.iba.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CompanyRestController {

    private CompanyService companyService;
    private UserService userService;

    @Autowired
    public CompanyRestController(CompanyService companyService, UserService userService) {
        this.companyService = companyService;
        this.userService = userService;
    }

    @GetMapping("/api/{page}")
    public List<CompanyResponseDto> getSinglePage(@PathVariable("page") int page) {

        int pageSize = AppConstants.COMPANIES_PAGE_SIZE;

        List<Company> companies = companyService.findActiveCompaniesByPageNumber(page, pageSize);

        List<CompanyResponseDto> list;

        if (companies == null || companies.isEmpty()) {
            list = Collections.emptyList();
        } else {
            list = companies.stream().map(CompanyResponseDto::of).collect(Collectors.toList());
        }
        return list;
    }

    @GetMapping("/api/donate/{companyId}/{amount}")
    public ResponseMessage donate(@PathVariable("companyId") Long companyId, @PathVariable("amount") Integer amount) {
        if (amount <= 0) {
            return new ResponseMessage(AppConstants.AMOUNT_MUST_BE_POSITIVE);
        }
        try {
            companyService.addDonate(companyId, amount);
        } catch (CompanyNotFoundException e) {
            return new ResponseMessage(AppConstants.COMPANY_ID_NOT_FOUND_MSG);
        }
        return new ResponseMessage(AppConstants.THANKS_FOR_DONATE);
    }

    @GetMapping("/api/join/{companyId}/{userId}")
    public ResponseMessage join(@PathVariable("companyId") Long companyId, @PathVariable("userId") Long userId) {
        try {
            User user = userService.findById(userId);
            String msg = companyService.addParticipant(companyId, user);
            return new ResponseMessage(msg);
        } catch (UserNotFoundException e) {
            return new ResponseMessage(AppConstants.FAIL_JOIN_COMPANY_MSG_SHOULD_LOG_IN);
        } catch (CompanyNotFoundException e) {
            return new ResponseMessage(e.getMessage());
        }
    }
}
