package by.mrbregovich.iba.project.service.impl;

import by.mrbregovich.iba.project.entity.Company;
import by.mrbregovich.iba.project.entity.CompanyStatus;
import by.mrbregovich.iba.project.repository.CompanyRepository;
import by.mrbregovich.iba.project.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Page<Company> findActiveByPage(Pageable pageable) {

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Company> companies = companyRepository.findAllByCompanyStatusOrderByCreatedAtDesc(CompanyStatus.ACTIVE);
        List<Company> list;
        if(companies == null){
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
}
