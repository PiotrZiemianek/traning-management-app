package pl.sda.training.management.app.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@Embeddable
@NoArgsConstructor
public class LastName {
    private String lastName;

    private LastName(String lastName) {
        this.lastName = lastName;
    }
    public static LastName of(String lastName){
        //validation
        return new LastName(lastName);
    }
    String value() {
        return lastName;
    }
}
