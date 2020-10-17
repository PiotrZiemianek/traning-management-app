package pl.sda.training.management.app.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@Embeddable
@NoArgsConstructor
public class BlockName {

    private String blockName;

    private BlockName(String blockName) {
        this.blockName = blockName;
    }

    public static BlockName of(String blockName) {
        //validation
        return new BlockName(blockName);
    }

    public String value() {
        return blockName;
    }
}
