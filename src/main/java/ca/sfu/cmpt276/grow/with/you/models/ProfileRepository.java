package ca.sfu.cmpt276.grow.with.you.models;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    Profile findByUsernameAndPassword(String username, String password);
}
