package pl.sda.training.management.app.domain;

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
