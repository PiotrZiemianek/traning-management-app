package pl.sda.training.management.app.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@Embeddable
@NoArgsConstructor
public class City {
    private String city;

    private City(String city) {
        this.city = city;
    }
    static City of(String city){
        //validation
        return new City(city);
    }
}
