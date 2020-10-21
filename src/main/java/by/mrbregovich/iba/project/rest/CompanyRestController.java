package by.mrbregovich.iba.project.rest;

import by.mrbregovich.iba.project.constants.AppConstants;
import by.mrbregovich.iba.project.entity.Company;
import by.mrbregovich.iba.project.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CompanyRestController {

    private CompanyService companyService;

    @Autowired
    public CompanyRestController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/api/{page}")
    public List<Company> getSinglePage(@PathVariable("page") int page) {

        int pageSize = AppConstants.COMPANIES_PAGE_SIZE;

        return companyService.findActiveCompaniesByPageNumber(page, pageSize);
    }
}
