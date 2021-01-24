package pl.sda.training.management.app.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({ElementType.TYPE})
@Constraint(validatedBy = UniqueCourseEditionValidator.class)
@Retention(RUNTIME)
public @interface UniqueCourseEdition {
    String message();
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}