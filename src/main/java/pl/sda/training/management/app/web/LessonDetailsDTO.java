package pl.sda.training.management.app.web;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LessonDetailsDTO {
    private Long id;
    private Long lessonId;
    private String dateTime;
    private int duration;
    private String trainerLogin;
    private String street;
    private String city;
    private String zipCode;
    private String roomNumber;

    public LessonDetailsDTO(LessonDTO lessonDTO) {
        this.lessonId = lessonDTO.getId();
    }
}
