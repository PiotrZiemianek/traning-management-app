package pl.sda.training.management.app.web;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class AppUserToGet {
    private Long id;
    private String login;
    private String fullName;
    private boolean isActive;
}
