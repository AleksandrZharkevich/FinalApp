package by.mrbregovich.iba.project.dto;

import by.mrbregovich.iba.project.constants.AppConstants;
import by.mrbregovich.iba.project.validator.BelarusRegion;
import by.mrbregovich.iba.project.validator.CellPhone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto {
    @NotBlank(message = AppConstants.VALIDATION_FIRST_NAME)
    private String firstname;
    @NotBlank(message = AppConstants.VALIDATION_LAST_NAME)
    private String lastname;
    @BelarusRegion(message = AppConstants.VALIDATION_REGION)
    private String region;
    @NotBlank(message = AppConstants.VALIDATION_DISTRICT)
    private String district;
    @NotBlank(message = AppConstants.VALIDATION_CITY)
    private String city;
    @NotBlank(message = AppConstants.VALIDATION_STREET_ADDRESS)
    private String streetAddress;
    @CellPhone(message = AppConstants.VALIDATION_PHONE_NUMBER)
    private String phoneNumber;
}
