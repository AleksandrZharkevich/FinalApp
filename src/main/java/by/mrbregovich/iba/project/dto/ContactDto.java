package by.mrbregovich.iba.project.dto;

import by.mrbregovich.iba.project.constants.AppConstants;
import by.mrbregovich.iba.project.validator.BelarusRegion;
import by.mrbregovich.iba.project.validator.CellPhone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto {
    //    @NotBlank(message = "{valid.firstname.notBlank}")
    @NotBlank(message = AppConstants.VALIDATION_FIRST_NAME)
//    @Size(min = 2, message = "{valid.firstname.size.min2}")
    private String firstname;
    //    @NotBlank(message = "{valid.lastname.notBlank}")
    @NotBlank(message = AppConstants.VALIDATION_LAST_NAME)
//    @Size(min = 2, message = "{valid.lastname.size.min2}")
    private String lastname;
    //    @BelarusRegion(message = "{valid.region.BelarusRegion}")
    @BelarusRegion(message = AppConstants.VALIDATION_REGION)
    private String region;
    //    @NotBlank(message = "{valid.district.notBlank}")
    @NotBlank(message = AppConstants.VALIDATION_DISTRICT)
    private String district;
    //    @NotBlank(message = "{valid.city.notBlank}")
    @NotBlank(message = AppConstants.VALIDATION_CITY)
    private String city;
    //    @NotBlank(message = "{valid.streetAddress.notBlank}")
    @NotBlank(message = AppConstants.VALIDATION_STREET_ADDRESS)
    private String streetAddress;
    //    @CellPhone(message = "{valid.phone.cellPhone}")
    @CellPhone(message = AppConstants.VALIDATION_PHONE_NUMBER)
    private String phoneNumber;
}
