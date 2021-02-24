package pl.sda.training.management.app.api.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.sda.training.management.app.domain.model.Course;
import pl.sda.training.management.app.domain.model.CourseName;

@Mapper(uses = LessonsBlockResourceMapper.class)
public interface CourseResourceMapper {

    CourseResourceMapper INSTANCE = Mappers.getMapper(CourseResourceMapper.class);

    @Mapping(target = "courseName", source = "name")
    CourseResource courseToDto(Course entity);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "courseName")
    Course resourceToCourse(CourseResource dto);

    @Mapping(target = "courseName", source = "value")
    CourseName map(String value);

    default String map(CourseName courseName) {
        return courseName == null ? null : courseName.value();
    }
}
