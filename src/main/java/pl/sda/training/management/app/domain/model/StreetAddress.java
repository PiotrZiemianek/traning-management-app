package pl.sda.training.management.app.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
public class StreetAddress implements Serializable {
    private String streetAddress;

    private StreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public static StreetAddress of(String streetAddress) {
        //validation
        return new StreetAddress(streetAddress);
    }
}
