package pl.sda.training.management.app.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@Embeddable
@NoArgsConstructor
public class RoomNumber {
    private String roomNumber;

    private RoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    static RoomNumber of(String roomNumber) {
        //validation
        return new RoomNumber(roomNumber);
    }
}
