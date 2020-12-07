package by.mrbregovich.iba.project.dto;

import by.mrbregovich.iba.project.constants.AppConstants;
import by.mrbregovich.iba.project.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class EditUserDto extends ContactDto {
    private Long id;
    private String login;
    private String password;
    @Email(message = AppConstants.VALIDATION_CORRECT_EMAIL)
    @NotBlank(message = AppConstants.VALIDATION_EMAIL)
    private String email;

    public static EditUserDto of(User user) {
        EditUserDto editUserDto = new EditUserDto();
        editUserDto.login = user.getLogin();
        editUserDto.email = user.getEmail();
        editUserDto.setFirstname(user.getContact().getFirstname());
        editUserDto.setLastname(user.getContact().getLastname());
        editUserDto.setRegion(user.getContact().getRegion());
        editUserDto.setDistrict(user.getContact().getDistrict());
        editUserDto.setCity(user.getContact().getCity());
        editUserDto.setStreetAddress(user.getContact().getStreetAddress());
        editUserDto.setPhoneNumber(user.getContact().getPhoneNumber());
        return editUserDto;
    }
}
