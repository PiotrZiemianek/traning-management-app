package pl.sda.training.management.app.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sda.training.management.app.domain.repository.UserRepo;

import javax.validation.Valid;

@Controller
@Slf4j
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {
    private final PasswordEncoder encoder;
    private final UserRepo userRepo;

    @GetMapping
    public String getRegisterView(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        return "register";
    }

    @PostMapping
    public String processRegistration(@Valid RegistrationForm registrationForm, Errors errors) {
        if (errors.hasErrors()) {
            return "register";
        }
        userRepo.save(registrationForm.toUser(encoder));
        log.info("User with login: " + registrationForm.getLogin() + " created and saved.");
        return "redirect:/login";
    }
}
