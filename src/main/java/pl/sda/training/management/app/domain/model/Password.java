package pl.sda.training.management.app.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@Embeddable
@NoArgsConstructor
public class Password {
    private String password;

    private Password(String password) {
        this.password = password;
    }

    public static Password of(String password){
        //validation
        return new Password(password);
    }

    String value() {
        return password;
    }
}
