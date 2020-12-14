package pl.sda.training.management.app.security;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.sda.training.management.app.domain.model.*;

import javax.validation.constraints.Size;

import static pl.sda.training.management.app.utils.Constants.*;

@Data
public class RegistrationForm {

    @Size(min = 3, message = LOGIN_AT_LEAST_3_CHAR)
    @Size(max = 16, message = LOGIN_NOT_LONGER_THAN_16_CHAR)
    private String login;

    @Size(min = 6, message = PASSWORD_AT_LEAST_6_CHAR)
    private String password;

    @Size(min = 2, message = NAME_TOO_SHORT)
    private String name;

    @Size(min = 2, message = LASTNAME_TOO_SHORT)
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
