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

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {
    private String name;
    private List<LessonsBlockDTO> lessonsBlocks = new ArrayList<>();

    public Course toCourse() {
        Course course = new Course(CourseName.of(name));
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
