package by.mrbregovich.iba.project.service;

import by.mrbregovich.iba.project.dto.CompanyDto;
import by.mrbregovich.iba.project.entity.Company;
import by.mrbregovich.iba.project.entity.User;
import by.mrbregovich.iba.project.exception.CompanyNotFoundException;

import java.util.List;

public interface CompanyService {

    List<Company> findActiveCompanies();

    List<Company> findActiveCompaniesByPageNumber(int pageNumber, int pageSize);

    Company register(CompanyDto form, User companyOwner);

    Company update(Company company, CompanyDto updatedForm);

    Company findCompanyById(Long id) throws CompanyNotFoundException;

    Company addDonate(Long id, Integer amount) throws CompanyNotFoundException;

    String addParticipant(Long companyId, User user) throws CompanyNotFoundException;

    String deleteParticipant(Long companyId, User user) throws CompanyNotFoundException;

    void checkExpiration();

    List<Company> findCreatedCompaniesByOwnerId(Long ownerId);

    List<Company> findJoinedCompaniesByParticipantId(Long userId);

    void closeCompany(Company company);
}
