package by.mrbregovich.iba.project.service;

import by.mrbregovich.iba.project.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyService {
    Page<Company> findActiveByPage(Pageable pageable);
}
