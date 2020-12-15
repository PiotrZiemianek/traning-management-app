package pl.sda.training.management.app.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.sda.training.management.app.domain.model.LessonDetails;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.format.DateTimeFormatter;

import static lombok.AccessLevel.PRIVATE;
import static pl.sda.training.management.app.utils.Constants.AT_LEAST_3_CHAR;
import static pl.sda.training.management.app.utils.Constants.INVALID_FORMAT;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@Builder
public class LessonDetailsDTO {
    private Long id;
    private Long lessonId;
    private String dateTime;
    private int duration;
    private String trainerLogin;
    @Size(min = 3, message = AT_LEAST_3_CHAR)
    private String street;
    @Size(min = 3, message = AT_LEAST_3_CHAR)
    private String city;
    @Pattern(regexp = "\\d{2}-\\d{3}",message = INVALID_FORMAT)
    private String zipCode;
    private String roomNumber;

    public LessonDetailsDTO(LessonDTO lessonDTO) {
        this.lessonId = lessonDTO.getId();
    }

    public static LessonDetailsDTO of(LessonDetails lessonDetails) {
        return LessonDetailsDTO.builder()
                .id(lessonDetails.getId())
                .lessonId(lessonDetails.getLesson().getId())
                .dateTime(lessonDetails.getLocalDateTime()
                        .format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")))
                .duration((int) lessonDetails.getDuration().toMinutes())
                .trainerLogin(lessonDetails.getTrainer().getUser().getLogin().value())
                .street(lessonDetails.getAddress().getStreetAddress().value())
                .city(lessonDetails.getAddress().getCity().value())
                .zipCode(lessonDetails.getAddress().getZipCode().value())
                .roomNumber(lessonDetails.getAddress().getRoomNumber().value())
                .build();
    }
}
