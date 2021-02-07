package pl.sda.training.management.app.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.sda.training.management.app.domain.model.Course;
import pl.sda.training.management.app.domain.model.CourseName;
import pl.sda.training.management.app.domain.model.LessonsBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor
public class CourseDTO {
    private Long id;
    private String name;
    private List<LessonsBlockDTO> lessonsBlocks = new ArrayList<>();

    public CourseDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static CourseDTO of(Course course) {
        return new CourseDTO(
                course.getId(),
                course.getName().value(),
                course.getLessonsBlocks().stream()
                        .map(LessonsBlockDTO::of)
                        .collect(Collectors.toList()));
    }

    public Course toCourse() {
        Course course = new Course(CourseName.of(name));
        course.setId(id);
        course.setLessonsBlocks(
                lessonsBlocks.stream()
                        .map(lessonsBlockDTO -> {
                            LessonsBlock lessonsBlock = lessonsBlockDTO.toLessonsBlock();
                            lessonsBlock.setCourse(course);
                            return lessonsBlock;
                        })
                        .collect(Collectors.toList())
        );
        return course;
    }
}
