package pl.sda.training.management.app.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@Embeddable
@NoArgsConstructor
public class StreetAddress {
    private String streetAddress;

    private StreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    static StreetAddress of(String streetAddress) {
        //validation
        return new StreetAddress(streetAddress);
    }
}