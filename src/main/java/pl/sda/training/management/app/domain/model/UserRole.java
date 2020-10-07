package pl.sda.training.management.app.domain.model;

import org.springframework.security.core.GrantedAuthority;

enum UserRole implements GrantedAuthority {
    /**
     * Admin can create and delete couches account.
     */
    //todo javadoc
    ROLE_ADMIN,
    ROLE_PARTICIPANT,
    ROLE_COACH;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
