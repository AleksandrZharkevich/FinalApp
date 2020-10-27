package by.mrbregovich.iba.project.service;

import by.mrbregovich.iba.project.dto.CompanyDto;
import by.mrbregovich.iba.project.entity.Company;
import by.mrbregovich.iba.project.entity.User;
import by.mrbregovich.iba.project.exception.CompanyNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanyService {

    Page<Company> findActiveCompaniesByPage(Pageable pageable);

    List<Company> findActiveCompaniesByPageNumber(int pageNumber, int pageSize);

    Company register(CompanyDto form, User companyOwner);

    Company findCompanyById(Long id) throws CompanyNotFoundException;

    Company addDonate(Long id, Integer amount) throws CompanyNotFoundException;
}
