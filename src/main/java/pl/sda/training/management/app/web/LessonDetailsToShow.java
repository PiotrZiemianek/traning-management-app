package pl.sda.training.management.app.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.sda.training.management.app.domain.model.LessonDetails;

import java.time.format.DateTimeFormatter;

import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@Builder
public class LessonDetailsToShow {
    private Long id;
    private String subject;
    private String dateTime;
    private int duration;
    private String trainerFullName;
    private String address;
    private String courseCode;

    public static LessonDetailsToShow of(LessonDetails lessonDetails) {
        return LessonDetailsToShow.builder()
                .id(lessonDetails.getId())
                .subject(lessonDetails.getLesson().getSubject().value())
                .dateTime(lessonDetails.getLocalDateTime()
                        .format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")))
                .duration((int) lessonDetails.getDuration().toMinutes())
                .trainerFullName(lessonDetails.getTrainer().getUser().getFullName())
                .address(lessonDetails.getAddress().getFullAddress())
                .courseCode(lessonDetails.getCourseEdition().getEditionCode().value())
                .build();
    }
}
