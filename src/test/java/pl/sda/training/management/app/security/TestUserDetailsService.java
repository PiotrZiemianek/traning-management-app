package pl.sda.training.management.app.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.sda.training.management.app.domain.model.User;

import java.util.List;

@RequiredArgsConstructor
public class TestUserDetailsService implements UserDetailsService {
    private final List<User> users;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        for (User user : users) {
            if (username.equals(user.getLogin().value())) {
                return user;
            }
        }
        throw new UsernameNotFoundException("User with login: " + username + " not found.");
    }
}
