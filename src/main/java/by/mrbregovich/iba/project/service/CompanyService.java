package by.mrbregovich.iba.project.service;

import by.mrbregovich.iba.project.dto.CompanyDto;
import by.mrbregovich.iba.project.entity.Company;
import by.mrbregovich.iba.project.entity.User;
import by.mrbregovich.iba.project.exception.CompanyNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyService {
    Page<Company> findActiveByPage(Pageable pageable);

    Company register(CompanyDto form, User companyOwner);

    Company findCompanyById(Long id) throws CompanyNotFoundException;
}
