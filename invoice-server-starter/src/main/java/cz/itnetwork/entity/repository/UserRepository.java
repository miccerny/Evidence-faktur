package cz.itnetwork.entity.repository;

import cz.itnetwork.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for user entities.
 * <p>
 * Provides methods for accessing users in the database by email and checking if a user exists.
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String username);

    boolean existsByEmail(String email);
}
