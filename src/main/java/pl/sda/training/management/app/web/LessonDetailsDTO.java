package pl.sda.training.management.app.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.sda.training.management.app.domain.model.LessonDetails;
import pl.sda.training.management.app.validation.groups.UpdatedInfo;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;
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
    @Size(min = 3, message = AT_LEAST_3_CHAR, groups = {UpdatedInfo.class, Default.class})
    private String street;
    @Size(min = 3, message = AT_LEAST_3_CHAR, groups = {UpdatedInfo.class, Default.class})
    private String city;
    @Pattern(regexp = "\\d{2}-\\d{3}", message = INVALID_FORMAT, groups = {UpdatedInfo.class, Default.class})
    private String zipCode;
    private String roomNumber;

    public LessonDetailsDTO(LessonDTO lessonDTO) {
        this.lessonId = lessonDTO.getId();
    }

    public static LessonDetailsDTO of(LessonDetails lessonDetails) {
        String trainerLogin = null;
        if (lessonDetails.getTrainer() != null) {
            trainerLogin = lessonDetails.getTrainer().getUser().getLogin().value();
        }

        return LessonDetailsDTO.builder()
                .id(lessonDetails.getId())
                .lessonId(lessonDetails.getLesson().getId())
                .dateTime(lessonDetails.getLocalDateTime()
                        .format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")))
                .duration((int) lessonDetails.getDuration().toMinutes())
                .trainerLogin(trainerLogin)
                .street(lessonDetails.getAddress().getStreetAddress().value())
                .city(lessonDetails.getAddress().getCity().value())
                .zipCode(lessonDetails.getAddress().getZipCode().value())
                .roomNumber(lessonDetails.getAddress().getRoomNumber().value())
                .build();
    }
}
