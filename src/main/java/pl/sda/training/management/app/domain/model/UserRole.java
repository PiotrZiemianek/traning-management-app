package pl.sda.training.management.app.domain.model;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    /**
     * Admin can create and delete trainers account.
     */
    ROLE_ADMIN,
    ROLE_PARTICIPANT,
    ROLE_TRAINER;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
