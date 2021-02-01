package pl.sda.training.management.app.security;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import pl.sda.training.management.app.domain.model.Login;
import pl.sda.training.management.app.domain.model.Password;
import pl.sda.training.management.app.domain.model.User;
import pl.sda.training.management.app.domain.model.UserRole;

import java.util.List;
import java.util.Set;

@TestConfiguration
public class WebSecurityTestConfig {
    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        User adminUser = User.builder()
                .login(Login.of("testAdmin"))
                .password(Password.of("testpass"))
                .roles(Set.of(UserRole.ROLE_ADMIN))
                .build();
        User trainerUser = User.builder()
                .login(Login.of("testTrainer"))
                .password(Password.of("testpass"))
                .roles(Set.of(UserRole.ROLE_TRAINER))
                .build();
        User studentUser = User.builder()
                .login(Login.of("testStudent"))
                .password(Password.of("testpass"))
                .roles(Set.of(UserRole.ROLE_PARTICIPANT))
                .build();

        return new TestUserDetailsService(List.of(
                adminUser, trainerUser, studentUser));
    }
}
