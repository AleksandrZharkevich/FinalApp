package by.mrbregovich.iba.project.service.impl;

import by.mrbregovich.iba.project.constants.AppConstants;
import by.mrbregovich.iba.project.dto.CompanyDto;
import by.mrbregovich.iba.project.entity.Company;
import by.mrbregovich.iba.project.entity.CompanyStatus;
import by.mrbregovich.iba.project.entity.RequestStatus;
import by.mrbregovich.iba.project.entity.User;
import by.mrbregovich.iba.project.exception.CompanyNotFoundException;
import by.mrbregovich.iba.project.repository.CompanyRepository;
import by.mrbregovich.iba.project.repository.RequestRepository;
import by.mrbregovich.iba.project.repository.UserRepository;
import by.mrbregovich.iba.project.service.CompanyService;
import by.mrbregovich.iba.project.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository;
    private UserRepository userRepository;
    private RequestRepository requestRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, UserRepository userRepository, RequestRepository requestRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.requestRepository = requestRepository;
    }

    @Override
    public Page<Company> findActiveCompaniesByPage(Pageable pageable) {

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Company> companies = companyRepository.findAllByCompanyStatusOrderByCreatedAtDesc(CompanyStatus.ACTIVE);
        List<Company> list;
        if (companies == null) {
            companies = Collections.emptyList();
        }
        if (companies.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, companies.size());
            list = companies.subList(startItem, toIndex);
        }

        return new PageImpl<Company>(list, PageRequest.of(currentPage, pageSize), companies.size());
    }

    @Override
    public List<Company> findActiveCompanies() {
        List<Company> companies = companyRepository.findAllByCompanyStatus(CompanyStatus.ACTIVE);
        return Objects.requireNonNullElse(companies, Collections.emptyList());
    }

    @Override
    public List<Company> findActiveCompaniesByPageNumber(int pageNumber, int pageSize) {
        List<Company> companies = companyRepository.findAllByCompanyStatusOrderByCreatedAtDesc(CompanyStatus.ACTIVE);
        List<Company> list;
        if (companies == null) {
            list = Collections.emptyList();
        } else {
            list = companies.stream().skip(pageSize * (pageNumber - 1)).limit(pageSize).collect(Collectors.toList());
        }
        return list;
    }

    @Override
    public Company register(CompanyDto form, User companyOwner) {
        Company newCompany = Mapper.map(form, Company.class);
        newCompany.setCompanyStatus(CompanyStatus.ACTIVE);
        newCompany.setOwner(companyOwner);
        if (form.getImgUrl() == null || form.getImgUrl().trim().isEmpty()) {
            newCompany.setImgUrl(AppConstants.NO_IMG_SRC);
        } else {
            newCompany.setImgUrl(form.getImgUrl());
        }
        newCompany.setCreatedAt(LocalDate.now());
        newCompany.setEndDate(LocalDate.now().plusDays(form.getDuration()));
        newCompany.setDonate(0);
        return companyRepository.save(newCompany);
    }

    @Override
    public Company update(Company company, CompanyDto updatedForm) {
        company.setImgUrl(updatedForm.getImgUrl());
        company.setName(updatedForm.getName());
        company.setDescription(updatedForm.getDescription());
        if (company.daysLeft() != updatedForm.getDuration()) {
            company.setEndDate(company.getEndDate().plusDays(updatedForm.getDuration()));
        }
        return companyRepository.save(company);
    }

    @Override
    public Company findCompanyById(Long id) throws CompanyNotFoundException {
        return companyRepository.findById(id).orElseThrow(() -> new CompanyNotFoundException(AppConstants.COMPANY_ID_NOT_FOUND_MSG));
    }

    @Override
    public Company addDonate(Long id, Integer amount) throws CompanyNotFoundException {
        Company company = companyRepository.findById(id).orElseThrow(() ->
                new CompanyNotFoundException(AppConstants.COMPANY_ID_NOT_FOUND_MSG));
        company.setDonate(company.getDonate() + amount);
        return companyRepository.save(company);
    }

    @Override
    public String addParticipant(Long companyId, User user) throws CompanyNotFoundException {
        Company company = companyRepository.findById(companyId).orElseThrow(() ->
                new CompanyNotFoundException(AppConstants.COMPANY_ID_NOT_FOUND_MSG));
        String resultMsg;
        if (company.getParticipants().contains(user)) {
            resultMsg = AppConstants.PARTICIPANT_ALREADY_JOINED;
        } else {
            company.getParticipants().add(user);
            companyRepository.save(company);
            resultMsg = AppConstants.OK_JOIN_COMPANY_MSG;
        }
        return resultMsg;
    }

    @Override
    public String deleteParticipant(Long companyId, User user) throws CompanyNotFoundException {
        Company company = companyRepository.findById(companyId).orElseThrow(() ->
                new CompanyNotFoundException(AppConstants.COMPANY_ID_NOT_FOUND_MSG));
        String resultMsg;
        if (!company.getParticipants().contains(user)) {
            resultMsg = AppConstants.PARTICIPANT_IS_NOT_JOINED;
        } else {
            //1. Удалить юзера из участников компании
            company.getParticipants().remove(user);
            //2. Удалить
            user.getRequests().forEach(request -> {
                if (request.getCompany().equals(company)) {
                    request.setRequestStatus(RequestStatus.REGISTERED);
                }
            });
            companyRepository.save(company);
            resultMsg = AppConstants.OK_QUIT_COMPANY_MSG;
        }
        return resultMsg;
    }

    @Override
    public void checkExpiration() {
        List<Company> companies = companyRepository.findAll();
        companies.forEach(company -> {
            if (company.daysLeft() < 1) {
                company.closeCompany();
                companyRepository.save(company);
            }
        });
    }

    @Override
    public List<Company> findCreatedCompaniesByOwnerId(Long ownerId) {
        return companyRepository.findAllByOwner_Id(ownerId).stream()
                .sorted((c1, c2) -> c2.getCreatedAt().compareTo(c1.getCreatedAt()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Company> findJoinedCompaniesByParticipantId(Long userId) {
        User participant = userRepository.findById(userId).get();
        return companyRepository.findAllByParticipantsContains(participant).stream()
                .sorted((c1, c2) -> c2.getCreatedAt().compareTo(c1.getCreatedAt()))
                .collect(Collectors.toList());
    }

    @Override
    public void closeCompany(Company company) {

    }
}
