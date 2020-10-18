package pl.sda.training.management.app.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseCreatorForm {
    private CourseRequest course;
    private LessonsBlockRequest lessonsBlock;
}
