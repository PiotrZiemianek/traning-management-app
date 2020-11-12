package pl.sda.training.management.app.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
public class City implements Serializable {
    private String city;

    private City(String city) {
        this.city = city;
    }
   public static City of(String city){
        //validation
        return new City(city);
    }

    public String value() {
        return city;
    }
}
