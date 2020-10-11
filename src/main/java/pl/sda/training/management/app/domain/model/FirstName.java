package pl.sda.training.management.app.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@Embeddable
@NoArgsConstructor
public class FirstName {

    private String firstName;

    private FirstName(String firstName) {
        this.firstName = firstName;
    }

    static FirstName of(String firstName) {
        //validation
        return new FirstName(firstName);
    }

    String value() {
        return firstName;
    }
}
