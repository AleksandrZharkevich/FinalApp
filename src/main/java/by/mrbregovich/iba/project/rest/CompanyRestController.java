package by.mrbregovich.iba.project.rest;

import by.mrbregovich.iba.project.constants.AppConstants;
import by.mrbregovich.iba.project.dto.CompanyRestResponseDto;
import by.mrbregovich.iba.project.dto.DonateDto;
import by.mrbregovich.iba.project.dto.ResponseMessage;
import by.mrbregovich.iba.project.entity.Company;
import by.mrbregovich.iba.project.exception.CompanyNotFoundException;
import by.mrbregovich.iba.project.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CompanyRestController {

    private CompanyService companyService;

    @Autowired
    public CompanyRestController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/api/{page}")
    public List<CompanyRestResponseDto> getSinglePage(@PathVariable("page") int page) {

        int pageSize = AppConstants.COMPANIES_PAGE_SIZE;

        List<Company> companies = companyService.findActiveCompaniesByPageNumber(page, pageSize);

        List<CompanyRestResponseDto> list;

        if (companies == null || companies.isEmpty()) {
            list = Collections.emptyList();
        } else {
            list = companies.stream().map(CompanyRestResponseDto::of).collect(Collectors.toList());
        }
        return list;
    }

    @GetMapping("/donate/{companyId}/{amount}")
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
}
