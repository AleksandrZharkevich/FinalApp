package by.mrbregovich.iba.project.controller;

import by.mrbregovich.iba.project.constants.AppConstants;
import by.mrbregovich.iba.project.dto.CompanyDto;
import by.mrbregovich.iba.project.dto.EditUserDto;
import by.mrbregovich.iba.project.dto.NewUserDto;
import by.mrbregovich.iba.project.entity.Company;
import by.mrbregovich.iba.project.entity.Request;
import by.mrbregovich.iba.project.entity.RequestStatus;
import by.mrbregovich.iba.project.entity.User;
import by.mrbregovich.iba.project.exception.CompanyNotFoundException;
import by.mrbregovich.iba.project.exception.UserAlreadyExistsException;
import by.mrbregovich.iba.project.exception.UserNotFoundException;
import by.mrbregovich.iba.project.service.CompanyService;
import by.mrbregovich.iba.project.service.RequestService;
import by.mrbregovich.iba.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class RegistrationController {

    private final UserService userService;
    private final CompanyService companyService;
    private final RequestService requestService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(UserService userService, PasswordEncoder passwordEncoder,
                                  CompanyService companyService, RequestService requestService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.companyService = companyService;
        this.requestService = requestService;
    }

    @GetMapping("/register")
    public ModelAndView registerForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("regForm", new NewUserDto()).setViewName("register");
        return modelAndView;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("errorMessage", AppConstants.CHECK_LOGIN_PASS_MSG);
        return "login";
    }

    @PostMapping("/register")
    public ModelAndView processRegistration(@Valid @ModelAttribute("regForm") NewUserDto form, Errors errors) {
        ModelAndView modelAndView = new ModelAndView();
        if (errors.hasErrors()) {
            modelAndView.setViewName("register");
        } else {
            try {
                userService.register(form);
                modelAndView.setViewName("login");
            } catch (UserAlreadyExistsException e) {
                modelAndView.addObject("errorMessage", e.getMessage());
                modelAndView.setViewName("register");
            }
        }
        return modelAndView;
    }

    @GetMapping("/profile")
    public String profile(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        model.addAttribute("createdCompanies", companyService.findCreatedCompaniesByOwnerId(user.getId()));
        model.addAttribute("joinedCompanies", companyService.findJoinedCompaniesByParticipantId(user.getId()));
        List<Request> allActiveRequests = requestService.findAllActiveRequestsByUser(user).stream()
                .sorted((r1, r2) -> r2.getPlacedAt().compareTo(r1.getPlacedAt()))
                .collect(Collectors.toList());
        model.addAttribute("allActiveRequests", allActiveRequests);
        return "profile";
    }

    @GetMapping("/profile/edit")
    public String editProfileForm(Model model, @AuthenticationPrincipal User auth) {
        try {
            User user = userService.findById(auth.getId());
            model.addAttribute("editUserDto", EditUserDto.of(auth));
            return "editProfile";
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return "redirect:/";
        }
    }

    @PostMapping("/profile/edit")
    public String processEditProfileForm(@Valid @ModelAttribute("editUserDto") EditUserDto form, Errors errors,
                                         @AuthenticationPrincipal User auth) {
        try {
            User user = userService.findById(auth.getId());
            userService.update(user, form);
            return "redirect:/profile";
        } catch (UserNotFoundException e) {
            return "redirect:/";
        }
    }

    @GetMapping("/profile/delete")
    public String closeCompany(@AuthenticationPrincipal User auth) {
        try {
            User user = userService.findById(auth.getId());
            //userService.deleteById(auth.getId());
            return "redirect:/";
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return "redirect:/";
        }
    }
}
