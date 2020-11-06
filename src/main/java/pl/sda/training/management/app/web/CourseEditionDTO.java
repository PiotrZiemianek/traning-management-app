package pl.sda.training.management.app.web;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
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
}
