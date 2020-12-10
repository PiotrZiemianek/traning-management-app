package pl.sda.training.management.app.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.sda.training.management.app.domain.model.User;

public class PrincipalRefresher {

    public static void refreshPrincipal(User user) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User && ((User) principal).getId().equals(user.getId())) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return;
        }
        throw new SecurityException("Unable to refresh principal with id: " + user.getId());
    }
}
