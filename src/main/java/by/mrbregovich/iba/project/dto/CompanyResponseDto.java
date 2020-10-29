package by.mrbregovich.iba.project.dto;

import by.mrbregovich.iba.project.entity.Company;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponseDto {

    private Long id;

    private String name;

    private String description;

    private String imgUrl;

    private String ownerLogin;

    private long ownerId;

    private int daysLeft;

    public static CompanyResponseDto of(Company company) {
        CompanyResponseDto companyResponseDto = new CompanyResponseDto();
        companyResponseDto.setId(company.getId());
        companyResponseDto.setName(company.getName());
        companyResponseDto.setDescription(company.getDescription());
        companyResponseDto.setImgUrl(company.getImgUrl());
        companyResponseDto.setOwnerLogin(company.getOwner().getLogin());
        companyResponseDto.setOwnerId(company.getOwner().getId());
        companyResponseDto.setDaysLeft(company.daysLeft());
        return companyResponseDto;
    }
}
