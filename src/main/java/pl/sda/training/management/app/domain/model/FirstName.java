package pl.sda.training.management.app.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
public class FirstName implements Serializable {

    private String firstName;

    private FirstName(String firstName) {
        this.firstName = firstName;
    }

    public static FirstName of(String firstName) {
        //validation
        return new FirstName(firstName);
    }

    public String value() {
        return firstName;
    }
}
