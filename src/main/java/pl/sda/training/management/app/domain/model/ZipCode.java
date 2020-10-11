package pl.sda.training.management.app.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@Embeddable
@NoArgsConstructor
public class ZipCode {
    private String zipCode;

    private ZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    static ZipCode of(String zipCode){
        //validation
        return new ZipCode(zipCode);
    }
}
