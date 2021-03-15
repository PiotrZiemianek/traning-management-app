package pl.sda.training.management.app.api.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import pl.sda.training.management.app.domain.model.BlockName;
import pl.sda.training.management.app.domain.model.Lesson;
import pl.sda.training.management.app.domain.model.LessonsBlock;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class LessonsBlockRequest {
    private final Long id;

    private final String name;

    private final List<LessonRequest> lessons;


    public static LessonsBlockRequest of(LessonsBlock lessonsBlock) {
        return new LessonsBlockRequest(
                lessonsBlock.getId(),
                lessonsBlock.getBlockName().value(),
                lessonsBlock.getLessons().stream()
                        .map(LessonRequest::of)
                        .collect(Collectors.toList()));
    }

    public LessonsBlock toLessonsBlock() {
        LessonsBlock lessonsBlock = new LessonsBlock(BlockName.of(name));
        lessonsBlock.setId(id);
        lessonsBlock.setLessons(lessons.stream()
                .map(lessonRequest -> {
                    Lesson lesson = lessonRequest.toLesson();
                    lesson.setLessonsBlock(lessonsBlock);
                    return lesson;
                })
                .collect(Collectors.toList())
        );
        return lessonsBlock;
    }
}
