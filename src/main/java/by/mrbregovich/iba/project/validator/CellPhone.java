package by.mrbregovich.iba.project.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CellPhone {
    String message() default "{Phone}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
