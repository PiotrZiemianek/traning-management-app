package pl.sda.training.management.app.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.sda.training.management.app.domain.model.User;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor(access = PRIVATE)
public class UserToShow {
    private String login;

    private String firstName;

    private String lastName;

    public static UserToShow of(User user) {
        return new UserToShow(
                user.getLogin().value(),
                user.getFirstName().value(),
                user.getLastName().value());
    }
}
