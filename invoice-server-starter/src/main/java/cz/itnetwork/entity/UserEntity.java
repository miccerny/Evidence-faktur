package cz.itnetwork.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Entity representing a user in the database.
 * <p>
 * Maps to the "users" table and is used for authentication and authorization.
 * Implements UserDetails so it can work with Spring Security.
 */
@Entity(name = "users")
@Getter
@Setter
public class UserEntity implements UserDetails {

    // Primary key, unique ID for the user. Generated automatically using a database sequence.
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", allocationSize = 1)
    private long userId;

    // User's email address. Must be unique and not null. Used as the username for login.
    @Column(nullable = false, unique = true)
    private String email;

    // User's password (encrypted). Must not be null.
    @Column(nullable = false)
    private String password;

    // Whether the user is an admin (true = admin, false = regular user). Must not be null.
    @Column(nullable = false)
    private boolean admin = false;

    // List of persons (contacts/companies) owned by this user.
    @OneToMany(mappedBy = "owner")
    private List<PersonEntity> persons;

    // Returns the username used for authentication (in this case, the email).
    @Override
    public String getUsername() {
        return email;
    }

    // Returns the user's authorities (roles) for Spring Security.
    // If the user is admin, they get ROLE_ADMIN, otherwise ROLE_USER.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + (admin ? "ADMIN" : "USER"));
        return List.of(grantedAuthority);
    }

    // The following methods are required by the UserDetails interface.
    // Here, they always return true, which means the account is always valid, not expired, not locked, and enabled.
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
        return true;
    }
}
