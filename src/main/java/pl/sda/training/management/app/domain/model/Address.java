package pl.sda.training.management.app.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class Address implements Serializable {
    @Embedded
    private City city;

    @Embedded
    private StreetAddress streetAddress;

    @Embedded
    private ZipCode zipCode;

    @Embedded
    private RoomNumber roomNumber;

    public String getFullAddress() {
        return streetAddress.value() + " " + roomNumber.value() + "; " + city.value() + " " + zipCode.value();
    }
}
