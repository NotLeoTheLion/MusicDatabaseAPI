package lt.viko.eif.groupproject.musicapi.repository;

import lt.viko.eif.groupproject.musicapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    boolean existsByUsername(String username);
}
