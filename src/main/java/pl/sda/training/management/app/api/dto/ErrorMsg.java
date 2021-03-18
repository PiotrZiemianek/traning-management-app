package pl.sda.training.management.app.api.dto;

import lombok.Data;

@Data(staticConstructor = "of")
public class ErrorMsg {
    private final String message;
}
