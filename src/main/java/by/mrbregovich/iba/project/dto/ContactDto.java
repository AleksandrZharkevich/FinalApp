package by.mrbregovich.iba.project.dto;

import by.mrbregovich.iba.project.validator.BelarusRegion;
import by.mrbregovich.iba.project.validator.CellPhone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto {
    @NotBlank(message = "{valid.firstname.notBlank}")
    @Size(min = 2, message = "{valid.firstname.size.min2}")
    private String firstname;
    @NotBlank(message = "{valid.lastname.notBlank}")
    @Size(min = 2, message = "{valid.lastname.size.min2}")
    private String lastname;
    @BelarusRegion(message = "{valid.region.BelarusRegion}")
    private String region;
    @NotBlank(message = "{valid.district.notBlank}")
    private String district;
    @NotBlank(message = "{valid.city.notBlank}")
    private String city;
    @NotBlank(message = "{valid.streetAddress.notBlank}")
    private String streetAddress;
    @CellPhone(message = "{valid.phone.cellPhone}")
    private String phoneNumber;
}
