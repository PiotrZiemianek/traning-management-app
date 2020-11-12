package pl.sda.training.management.app.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
public class RoomNumber implements Serializable {
    private String roomNumber;

    private RoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public static RoomNumber of(String roomNumber) {
        //validation
        return new RoomNumber(roomNumber);
    }

    public String value() {
        return roomNumber;
    }
}
