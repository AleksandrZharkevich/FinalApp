package by.mrbregovich.iba.project.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<CellPhone, String> {
    @Override
    public void initialize(CellPhone constraintAnnotation) {
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext ctx) {
        if (phoneNumber == null) {
            return false;
        }
        //+375291234567
        return phoneNumber.matches("\\+375(29|44|33|25)\\d{7}");
    }
}