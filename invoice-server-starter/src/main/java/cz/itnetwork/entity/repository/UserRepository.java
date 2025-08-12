package cz.itnetwork.entity.repository;

import cz.itnetwork.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Finds a user by their email address.
     *
     * @param email the email address to search for
     * @return an Optional containing the found user, or empty if no user is found
     */
    Optional<UserEntity> findByEmail(String email);

    /**
     * Checks whether a user with the given email address exists.
     *
     * @param email the email address to check
     * @return true if a user with the given email exists, false otherwise
     */
    boolean existsByEmail(String email);
}
