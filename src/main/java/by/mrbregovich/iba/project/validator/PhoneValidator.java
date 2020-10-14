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
        //+375291234567 || 375291234567
        if (phoneNumber.matches("\\+?375(29|44|33|25)\\d{7}")) {
            return true;
            //+375(29)1234567 || 375(29)1234567
        } else if (phoneNumber.matches("\\+?375\\((29|44|33|25)\\)\\d{7}")) {
            return true;
            //+375-29-123-45-67 || 375-29-123-45-67
        } else if (phoneNumber.matches("\\+?375-(29|44|33|25)-\\d{3}-\\d{2}-\\d{2}")) {
            return true;
        }
        return false;
    }
}