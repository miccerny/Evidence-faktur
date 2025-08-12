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
 * Entity representing an application user.
 * <p>
 * This entity is mapped to the "users" table and stores authentication
 * and authorization details, as well as related owned records.
 * Implements the {@link UserDetails} interface for Spring Security integration.
 */
@Entity(name = "users")
@Getter
@Setter
public class UserEntity implements UserDetails {

    /** Unique identifier of the user (primary key). */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", allocationSize = 1)
    private long userId;

    /** Email address used as the username for authentication. Must be unique. */
    @Column(nullable = false, unique = true)
    private String email;

    /** Encrypted password for the user account. */
    @Column(nullable = false)
    private String password;

    /** Flag indicating if the user has administrator privileges. */
    @Column(nullable = false)
    private boolean admin = false;

    /** List of persons (companies) owned by this user. */
    @OneToMany(mappedBy = "owner")
    private List<PersonEntity> persons;

    /**
     * Returns the username used for authentication, which in this case is the email.
     *
     * @return the user's email address
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Returns the authorities granted to the user.
     * <p>
     * If the {@code admin} flag is true, the user will have {@code ROLE_ADMIN},
     * otherwise they will have {@code ROLE_USER}.
     *
     * @return a collection containing the user's granted authority
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + (admin ? "ADMIN" : "USER"));
        return List.of(grantedAuthority);
    }

    /** Indicates whether the user's account has expired. Always returns true (non-expired). */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /** Indicates whether the user's account is locked. Always returns true (non-locked). */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /** Indicates whether the user's credentials (password) have expired. Always returns true (non-expired). */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /** Indicates whether the user's account is enabled. Always returns true (enabled). */
    @Override
    public boolean isEnabled() {
        return true;
    }
}