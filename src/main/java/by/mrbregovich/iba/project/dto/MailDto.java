package by.mrbregovich.iba.project.dto;

import by.mrbregovich.iba.project.constants.AppConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailDto {
    @NotBlank(message = AppConstants.VALIDATION_FIRST_NAME)
    private String firstName;

    @NotBlank(message = AppConstants.VALIDATION_LAST_NAME)
    private String lastName;

    @NotBlank(message = AppConstants.VALIDATION_EMAIL)
    @Email(message = AppConstants.VALIDATION_CORRECT_EMAIL)
    private String email;

    @NotBlank(message = AppConstants.VALIDATION_MAIL_SUBJECT)
    private String subject;

    @NotBlank(message = AppConstants.VALIDATION_MAIL_CONTENT)
    private String content;
}
