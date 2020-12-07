package by.mrbregovich.iba.project.dto;

import by.mrbregovich.iba.project.constants.AppConstants;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class NewUserDto extends ContactDto {
    @NotBlank(message = AppConstants.VALIDATION_LOGIN)
    private String login;
    @NotBlank(message = AppConstants.VALIDATION_PASSWORD)
    private String password;
    @Email(message = AppConstants.VALIDATION_CORRECT_EMAIL)
    @NotBlank(message = AppConstants.VALIDATION_EMAIL)
    private String email;

    public NewUserDto(String firstname, String lastname, String region, String district, String city,
                      String streetAddress, String phoneNumber, String login, String password, String email) {
        super(firstname, lastname, region, district, city, streetAddress, phoneNumber);
        this.login = login;
        this.password = password;
        this.email = email;
    }
}
