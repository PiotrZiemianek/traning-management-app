package pl.sda.training.management.app.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sda.training.management.app.domain.repository.UserRepo;

@Controller
@Slf4j
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {
    private final PasswordEncoder encoder;
    private final UserRepo userRepo;

    @GetMapping
    public String getRegisterView() {
        return "register";
    }

    @PostMapping
    public String processRegistration(RegistrationForm registrationForm) {
        userRepo.save(registrationForm.toUser(encoder));
        log.info("User with login: " + registrationForm.getLogin() + "created and saved.");
        return "redirect:/login";
    }
}
