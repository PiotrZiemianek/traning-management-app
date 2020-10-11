package pl.sda.training.management.app.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class Address {
    @Embedded
    private City city;

    @Embedded
    private StreetAddress streetAddress;

    @Embedded
    private ZipCode zipCode;

    @Embedded
    private RoomNumber roomNumber;
}