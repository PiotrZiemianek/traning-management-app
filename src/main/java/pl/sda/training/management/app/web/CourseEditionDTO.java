package pl.sda.training.management.app.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.sda.training.management.app.domain.model.CourseEdition;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
public class CourseEditionDTO {
    private Long id;
    private Long courseId;
    private String editionCode;
    private List<LessonDetailsDTO> lessonsDetails = new ArrayList<>();

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
