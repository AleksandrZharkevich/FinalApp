package by.mrbregovich.iba.project.controller;

import by.mrbregovich.iba.project.constants.AppConstants;
import by.mrbregovich.iba.project.dto.CompanyDto;
import by.mrbregovich.iba.project.entity.Company;
import by.mrbregovich.iba.project.entity.Request;
import by.mrbregovich.iba.project.entity.RequestStatus;
import by.mrbregovich.iba.project.entity.User;
import by.mrbregovich.iba.project.exception.CompanyNotFoundException;
import by.mrbregovich.iba.project.service.CompanyService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class CompanyController {

    private CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

//    @GetMapping(value = {"/", "/index"})
//    public String listCompanies(Model model,
//                                @RequestParam("page") Optional<Integer> page) {
//        int currentPage = page.orElse(1);
//        int pageSize = AppConstants.COMPANIES_PAGE_SIZE;
//
//        Page<Company> companyPage = companyService.findActiveCompaniesByPage(PageRequest.of(currentPage - 1, pageSize));
//
//        model.addAttribute("companyPage", companyPage);
//
//        int totalPages = companyPage.getTotalPages();
//        if (totalPages > 0) {
//            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
//
//            model.addAttribute("pageNumbers", pageNumbers);
//        }
//
//        return "index";
//    }

    @GetMapping(value = {"/", "/index"})
    public String listCompanies(Model model) {
        int startPage = 1;
        int pageSize = AppConstants.COMPANIES_PAGE_SIZE;

        List<Company> activeCompanies = companyService.findActiveCompaniesByPageNumber(startPage, pageSize)
                .stream()
                .limit(AppConstants.COMPANIES_PAGE_SIZE)
                .collect(Collectors.toList());

        model.addAttribute("activeCompanies", activeCompanies);

        int totalActiveCompaniesCount = companyService.findActiveCompanies().size();
        List<Integer> pageNumbers = new ArrayList<>();
        if (totalActiveCompaniesCount > AppConstants.COMPANIES_PAGE_SIZE) {
            int totalPages = (int) Math.ceil(totalActiveCompaniesCount / (AppConstants.COMPANIES_PAGE_SIZE * 1.0));
            pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
        }
        model.addAttribute("pageNumbers", pageNumbers);

        return "index";
    }

    @GetMapping("/addCompany")
    public String addCompany(Model model) {
        model.addAttribute("companyDto", new CompanyDto());
        return "companyForm";
    }

    @PostMapping("/addCompany")
    public ModelAndView processCompany(@Valid @ModelAttribute("companyDto") CompanyDto form, Errors errors,
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
            List<Request> activeRequests = company.getRequests().stream()
                    .filter(request -> request.getRequestStatus() == RequestStatus.REGISTERED)
                    .sorted((r1, r2) -> r2.getPlacedAt().compareTo(r1.getPlacedAt()))
                    .limit(AppConstants.REQUESTS_PAGE_SIZE)
                    .collect(Collectors.toList());
            model.addAttribute("activeRequests", activeRequests);
            long totalRequestsCount = company.getRequests().stream()
                    .filter(request -> request.getRequestStatus() == RequestStatus.REGISTERED).count();
            List<Integer> pageNumbers = new ArrayList<>();
            if (totalRequestsCount > AppConstants.REQUESTS_PAGE_SIZE) {
                int totalPages = (int) Math.ceil(totalRequestsCount / (AppConstants.REQUESTS_PAGE_SIZE * 1.0));
                pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            }
            model.addAttribute("pageNumbers", pageNumbers);
            return "single-company";
        } catch (CompanyNotFoundException e) {
//            modelAndView.addObject("errorMsg", e.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping("/companies/{id}/edit")
    public String editCompanyForm(Model model, @PathVariable("id") Long id, @AuthenticationPrincipal User user) {
        try {
            Company company = companyService.findCompanyById(id);
            if (!company.getOwner().equals(user)) {
                return "redirect:/";
            }
            model.addAttribute("companyDto", CompanyDto.of(company));
            return "editCompany";
        } catch (CompanyNotFoundException e) {
            return "redirect:/";
        }
    }

    @PostMapping("/companies/{id}/edit")
    public String processEditCompanyForm(@Valid @ModelAttribute("companyDto") CompanyDto form, Errors errors,
                                         @PathVariable("id") Long companyId, @AuthenticationPrincipal User user) {
        try {
            Company company = companyService.findCompanyById(companyId);
            if (!company.getOwner().equals(user)) {
                return "redirect:/";
            }
            companyService.update(company, form);
            return "redirect:/companies/" + companyId;
        } catch (CompanyNotFoundException e) {
            return "redirect:/";
        }
    }

    @GetMapping("/companies/{companyId}/close")
    public String closeCompany(Model model, @PathVariable("companyId") Long companyId, @AuthenticationPrincipal User user) {
        try {
            Company company = companyService.findCompanyById(companyId);
            if (!company.getOwner().equals(user)) {
                return "redirect:/";
            }
//            companyService.closeCompany(company);
            return "redirect:/companies/" + companyId;
        } catch (CompanyNotFoundException e) {
            return "redirect:/";
        }
    }
}
