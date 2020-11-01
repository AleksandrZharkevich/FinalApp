package by.mrbregovich.iba.project.service.impl;

import by.mrbregovich.iba.project.constants.AppConstants;
import by.mrbregovich.iba.project.dto.CompanyDto;
import by.mrbregovich.iba.project.entity.Company;
import by.mrbregovich.iba.project.entity.CompanyStatus;
import by.mrbregovich.iba.project.entity.User;
import by.mrbregovich.iba.project.exception.CompanyNotFoundException;
import by.mrbregovich.iba.project.repository.CompanyRepository;
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
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
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
}
