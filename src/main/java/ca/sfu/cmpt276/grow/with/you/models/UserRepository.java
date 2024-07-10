package ca.sfu.cmpt276.grow.with.you.models;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import ca.sfu.cmpt276.utils.enums.UserRole;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByUsernameAndPassword(String username, String password);

    List<User> findByRole(UserRole role);
}