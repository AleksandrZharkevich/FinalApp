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
        newCompany.setImgUrl(AppConstants.NO_IMG_SRC);
        newCompany.setCreatedAt(LocalDate.now());
        newCompany.setEndDate(LocalDate.now().plusDays(form.getDuration()));
        return companyRepository.save(newCompany);
    }

    @Override
    public Company findCompanyById(Long id) throws CompanyNotFoundException {
        return companyRepository.findById(id).orElseThrow(() -> new CompanyNotFoundException(AppConstants.COMPANY_ID_NOT_FOUND_MSG));
    }
}
