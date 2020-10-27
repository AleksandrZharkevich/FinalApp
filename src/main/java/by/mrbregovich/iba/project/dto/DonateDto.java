package by.mrbregovich.iba.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonateDto {
    private String donateAmount;
    private String companyId;
}
