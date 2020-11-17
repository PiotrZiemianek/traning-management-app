package pl.sda.training.management.app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "training.date")
public class DateTimeFormat {
    private String format = "dd.MM.yyyy HH:mm";
}
