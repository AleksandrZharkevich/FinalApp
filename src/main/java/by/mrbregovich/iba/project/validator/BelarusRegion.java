package by.mrbregovich.iba.project.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BelarusRegionValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BelarusRegion {
    String message() default "{Region}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}