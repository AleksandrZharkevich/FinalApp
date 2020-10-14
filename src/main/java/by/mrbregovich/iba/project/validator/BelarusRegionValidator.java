package by.mrbregovich.iba.project.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BelarusRegionValidator implements ConstraintValidator<BelarusRegion, String> {
    @Override
    public void initialize(BelarusRegion constraintAnnotation) {
    }

    @Override
    public boolean isValid(String region, ConstraintValidatorContext ctx) {
        if (region == null) {
            return false;
        }
        region = region.toLowerCase();
        if (region.equals("брестская") || region.equals("витебская")
                || region.equals("гомельская") || region.equals("гродненская")
                || region.equals("минская") || region.equals("могилевская")) {
            return true;
        }
        return false;
    }
}
