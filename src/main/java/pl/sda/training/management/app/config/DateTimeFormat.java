package pl.sda.training.management.app.config;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class DateTimeFormat {
    private String format = "dd.MM.yyyy HH:mm";
}
