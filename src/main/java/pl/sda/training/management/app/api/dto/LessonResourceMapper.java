package pl.sda.training.management.app.api.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.sda.training.management.app.domain.model.Lesson;
import pl.sda.training.management.app.domain.model.LessonSubject;

@Mapper
public interface LessonResourceMapper {

    LessonResourceMapper INSTANCE = Mappers.getMapper(LessonResourceMapper.class);

    LessonResource lessonToDto(Lesson entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lessonsBlock", ignore = true)
    Lesson resourceToLesson(LessonResource dto);

    @Mapping(target = "lessonSubject", source = "value")
    LessonSubject stringToLessonSubject(String value);

    default String lessonSubjectToString(LessonSubject lessonSubject) {
        return lessonSubject == null ? null : lessonSubject.value();
    }
}
