package by.mrbregovich.iba.project.dto;

import by.mrbregovich.iba.project.entity.Company;
import by.mrbregovich.iba.project.entity.Request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRestResponseDto {

    private Long id;

    private String name;

    private String imgUrl;

    private String ownerLogin;

    private long ownerId;

    private int daysLeft;

    public static CompanyRestResponseDto of(Company company) {
        CompanyRestResponseDto companyRestResponseDto = new CompanyRestResponseDto();
        companyRestResponseDto.setId(company.getId());
        companyRestResponseDto.setName(company.getName());
        companyRestResponseDto.setImgUrl(company.getImgUrl());
        companyRestResponseDto.setOwnerLogin(company.getOwner().getLogin());
        companyRestResponseDto.setOwnerId(company.getOwner().getId());
        companyRestResponseDto.setDaysLeft(company.daysLeft());
        return companyRestResponseDto;
    }
}
