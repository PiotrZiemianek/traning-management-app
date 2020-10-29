package pl.sda.training.management.app.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.training.management.app.domain.model.UserRole;
import pl.sda.training.management.app.domain.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserWebService {
    private final UserService userService;


    public List<UserToShow> getUsersToShow() {
        return userService.getAllUsers().stream()
                .filter(user -> !user.getRoles().contains(UserRole.ROLE_TRAINER))
                .map(UserToShow::of)
                .collect(Collectors.toList());
    }
}
