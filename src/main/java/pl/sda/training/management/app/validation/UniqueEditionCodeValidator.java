package pl.sda.training.management.app.validation;

import lombok.RequiredArgsConstructor;
import pl.sda.training.management.app.domain.model.EditionCode;
import pl.sda.training.management.app.domain.service.CourseEditionService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UniqueEditionCodeValidator implements ConstraintValidator<UniqueEditionCode, String> {

    private final CourseEditionService courseEditionService;

    @Override
    public void initialize(UniqueEditionCode uniqueEditionCode) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !courseEditionService.existsByEditionCode(EditionCode.of(value));

    }
}
