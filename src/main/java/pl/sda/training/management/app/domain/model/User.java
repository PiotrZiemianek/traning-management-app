package pl.sda.training.management.app.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User extends AbstractEntity implements UserDetails {

    @Embedded
    private Login login;

    @Embedded
    private Password password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles = new HashSet<>();

    @Embedded
    private FirstName firstName;

    @Embedded
    private LastName lastName;

    private boolean isActive = false;

    @OneToOne(mappedBy = "user", orphanRemoval = true)
    private UserNotification notifications;

    @Builder
    private User(Long id, Login login, Password password, Set<UserRole> roles, FirstName firstName, LastName lastName, boolean isActive, UserNotification notifications) {
        super(id);
        this.login = login;
        this.password = password;
        if (roles != null) this.roles = roles;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isActive = isActive;
        this.notifications = notifications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!Objects.equals(id, user.id)) return false;
        return login.equals(user.login);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + login.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", role=" + roles +
                ", isActive=" + isActive +
                '}';
    }

    public String getFullName() {
        return firstName.value() + " " + lastName.value();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return login.value();
    }

    @Override
    public String getPassword() {
        return password.value();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

}
