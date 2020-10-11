package pl.sda.training.management.app.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.training.management.app.domain.model.User;
import pl.sda.training.management.app.domain.repository.UserRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;


    public List<User> getAllUsers() {
        return userRepo.findAll();
    }


}
