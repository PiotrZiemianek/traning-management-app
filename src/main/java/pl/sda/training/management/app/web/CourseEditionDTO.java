package pl.sda.training.management.app.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.sda.training.management.app.domain.model.CourseEdition;
import pl.sda.training.management.app.validation.UniqueCourseEdition;
import pl.sda.training.management.app.validation.UniqueEditionCode;
import pl.sda.training.management.app.validation.groups.UpdatedInfo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;
import static pl.sda.training.management.app.utils.Constants.EDITION_CODE_INVALID_FORMAT;
import static pl.sda.training.management.app.utils.Constants.NON_UNIQUE_EDITION_CODE;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@Builder
@UniqueCourseEdition(message = NON_UNIQUE_EDITION_CODE, groups = UpdatedInfo.class)
public class CourseEditionDTO {
    private Long id;
    @NotNull
    private Long courseId;
    @Pattern(regexp = "[a-z]+[A-Z]{3}\\d+", message = EDITION_CODE_INVALID_FORMAT,
            groups = {UpdatedInfo.class, Default.class})
    @UniqueEditionCode(message = NON_UNIQUE_EDITION_CODE)
    private String editionCode;
    private List<@Valid LessonDetailsDTO> lessonsDetails = new ArrayList<>();

    public CourseEditionDTO(CourseDTO courseDTO) {
        this.courseId = courseDTO.getId();
        courseDTO.getLessonsBlocks().forEach(lessonsBlockDTO -> {
            lessonsBlockDTO.getLessons()
                    .stream()
                    .map(LessonDetailsDTO::new)
                    .forEach(this.lessonsDetails::add);
        });
    }

    public static CourseEditionDTO of(CourseEdition courseEdition) {
        return new CourseEditionDTO(
                courseEdition.getId(),
                courseEdition.getCourse().getId(),
                courseEdition.getEditionCode().value(),
                courseEdition.getLessonsDetails()
                        .stream()
                        .map(LessonDetailsDTO::of)
                        .collect(Collectors.toList())
        );
    }
}
