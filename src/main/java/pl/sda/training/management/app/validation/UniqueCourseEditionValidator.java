package pl.sda.training.management.app.validation;

import lombok.RequiredArgsConstructor;
import pl.sda.training.management.app.domain.model.CourseEdition;
import pl.sda.training.management.app.domain.model.EditionCode;
import pl.sda.training.management.app.domain.service.CourseEditionService;
import pl.sda.training.management.app.web.CourseEditionDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

@RequiredArgsConstructor
public class UniqueCourseEditionValidator implements ConstraintValidator<UniqueCourseEdition, CourseEditionDTO> {

    private final CourseEditionService courseEditionService;

    @Override
    public void initialize(UniqueCourseEdition uniqueCourseEdition) {
    }

    @Override
    public boolean isValid(CourseEditionDTO value, ConstraintValidatorContext context) {
        Optional<CourseEdition> editionOptional = courseEditionService.findByEditionCode(EditionCode.of(value.getEditionCode()));


        return editionOptional.isEmpty()||editionOptional.get().getId().equals(value.getId());
    }
}
