package by.mrbregovich.iba.project.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class NewUserDto extends ContactDto {
    @NotBlank(message = "{valid.login.notBlank}")
    @Size(min = 2, message = "{valid.login.size.min2}")
    private String login;
    @NotBlank(message = "{valid.password.notBlank}")
    @Size(min = 5, message = "{valid.password.size.min5}")
    private String password;
    @Email(message = "{valid.email.invalidEmail}")
    private String email;

    public NewUserDto(String firstname, String lastname, String region, String district, String city,
                      String streetAddress, String phoneNumber, String login, String password, String email) {
        super(firstname, lastname, region, district, city, streetAddress, phoneNumber);
        this.login = login;
        this.password = password;
        this.email = email;
    }
}
