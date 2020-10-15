package by.mrbregovich.iba.project.dto;

import by.mrbregovich.iba.project.cont.AppConstants;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class NewUserDto extends ContactDto {
    //    @Size(min = 2, message = "{valid.login.size.min2}")
    @NotBlank(message = AppConstants.VALIDATION_LOGIN)
    private String login;
    //    @Size(min = 5, message = "{valid.password.size.min5}")
    @NotBlank(message = AppConstants.VALIDATION_PASSWORD)
    private String password;
    //    @Email(message = "{valid.email.invalidEmail}")
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
