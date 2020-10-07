package pl.sda.training.management.app.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Embeddable
@NoArgsConstructor
public class Login {

    @Column(unique = true)
    private String login;

    private Login(String login) {
        this.login = login;
    }

    public static Login of(String login) {
        //validation
        return new Login(login);
    }

    String value() {
        return login;
    }
}
