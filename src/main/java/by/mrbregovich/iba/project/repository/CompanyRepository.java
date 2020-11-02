package by.mrbregovich.iba.project.repository;

import by.mrbregovich.iba.project.entity.Company;
import by.mrbregovich.iba.project.entity.CompanyStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    List<Company> findAllByCompanyStatusOrderByCreatedAtDesc(CompanyStatus companyStatus);

    List<Company> findAllByCompanyStatus(CompanyStatus companyStatus);
}
