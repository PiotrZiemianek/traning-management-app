package pl.sda.training.management.app.security;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.sda.training.management.app.domain.model.*;

@Data
public class RegistrationForm {
    private String login;

    private String password;

    private String name;

    private String lastName;

    public User toUser(PasswordEncoder encoder) {
        return User.builder()
                .login(Login.of(login))
                .password(Password.of(encoder.encode(password)))
                .firstName(FirstName.of(name))
                .lastName(LastName.of(lastName))
                .isActive(true)
                .build();
    }
}
