package talentsync.moseshunsu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talentsync.moseshunsu.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByPhoneNumber(String phoneNumber);
    Optional<User> findByUsernameOrEmail(String username, String Email);
}
