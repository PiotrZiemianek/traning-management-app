package pl.sda.training.management.app.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@Embeddable
@NoArgsConstructor
public class EditionCode {

    private String editionCode;

    private EditionCode(String editionCode) {
        this.editionCode = editionCode;
    }

    static EditionCode of(String editionCode) {
        //validation
        return new EditionCode(editionCode);
    }

    String value() {
        return editionCode;
    }
}
