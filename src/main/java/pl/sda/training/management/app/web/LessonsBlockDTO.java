package pl.sda.training.management.app.web;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.sda.training.management.app.domain.model.BlockName;
import pl.sda.training.management.app.domain.model.Lesson;
import pl.sda.training.management.app.domain.model.LessonSubject;
import pl.sda.training.management.app.domain.model.LessonsBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class LessonsBlockDTO {

    private String name;

    private List<String> lessons = new ArrayList<>();

    public LessonsBlockDTO(String name) {
        this.name = name;
    }


    public LessonsBlock toLessonsBlock() {
        LessonsBlock lessonsBlock = new LessonsBlock(BlockName.of(name));
        lessonsBlock.setLessons(lessons.stream()
                .map(s -> {
                    Lesson lesson = new Lesson(LessonSubject.of(s));
                    lesson.setLessonsBlock(lessonsBlock);
                    return lesson;
                })
                .collect(Collectors.toList())
        );
        return lessonsBlock;
    }
}
