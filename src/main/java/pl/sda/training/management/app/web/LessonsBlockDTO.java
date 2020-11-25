package pl.sda.training.management.app.web;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.sda.training.management.app.domain.model.BlockName;
import pl.sda.training.management.app.domain.model.Lesson;
import pl.sda.training.management.app.domain.model.LessonsBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
public class LessonsBlockDTO {

    private Long id;

    private String name;

    private List<LessonDTO> lessons = new ArrayList<>();


    public static LessonsBlockDTO of(LessonsBlock lessonsBlock) {
        return new LessonsBlockDTO(
                lessonsBlock.getId(),
                lessonsBlock.getBlockName().value(),
                lessonsBlock.getLessons().stream()
                        .map(LessonDTO::of)
                        .collect(Collectors.toList()));
    }

    public LessonsBlock toLessonsBlock() {
        LessonsBlock lessonsBlock = new LessonsBlock(BlockName.of(name));
        lessonsBlock.setId(id);
        lessonsBlock.setLessons(lessons.stream()
                .map(lessonDTO -> {
                    Lesson lesson = lessonDTO.toLesson();
                    lesson.setLessonsBlock(lessonsBlock);
                    return lesson;
                })
                .collect(Collectors.toList())
        );
        return lessonsBlock;
    }
}
