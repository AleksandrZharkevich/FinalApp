package by.mrbregovich.iba.project.controller;

import by.mrbregovich.iba.project.constants.AppConstants;
import by.mrbregovich.iba.project.dto.CompanyDto;
import by.mrbregovich.iba.project.entity.Company;
import by.mrbregovich.iba.project.entity.User;
import by.mrbregovich.iba.project.exception.CompanyNotFoundException;
import by.mrbregovich.iba.project.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class CompanyController {

    private CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping(value = {"/", "/index"})
    public String listCompanies(Model model,
                                @RequestParam("page") Optional<Integer> page) {
        int currentPage = page.orElse(1);
        int pageSize = AppConstants.COMPANIES_PAGE_SIZE;

        Page<Company> companyPage = companyService.findActiveCompaniesByPage(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("companyPage", companyPage);

        int totalPages = companyPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());

            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "index";
    }

    @GetMapping("/addCompany")
    public String addCompany(Model model) {
        model.addAttribute("companyForm", new CompanyDto());
        return "companyForm";
    }

    @PostMapping("/addCompany")
    public ModelAndView processCompany(@Valid @ModelAttribute("companyForm") CompanyDto form, Errors errors,
                                       @AuthenticationPrincipal User user) {
        ModelAndView modelAndView = new ModelAndView();
        if (errors.hasErrors()) {
            modelAndView.setViewName("companyForm");
        } else {
            Company company = companyService.register(form, user);

            modelAndView.setViewName("redirect:/");
        }
        return modelAndView;
    }

    @GetMapping("/companies/{id}")
    public String companyDetail(Model model, @PathVariable("id") Long id) {
        try {
            Company company = companyService.findCompanyById(id);
            model.addAttribute("company", company);
            return "single-company";
        } catch (CompanyNotFoundException e) {
//            modelAndView.addObject("errorMsg", e.getMessage());
        }
        return "redirect:/";
    }
}
