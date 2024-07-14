package ca.sfu.cmpt276.grow.with.you.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.sfu.cmpt276.grow.with.you.models.User;
import ca.sfu.cmpt276.grow.with.you.models.UserRepository;
import jakarta.servlet.http.HttpSession;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(int id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public User getUserBySession(HttpSession session) {
        User user = (User) session.getAttribute("session_user");
        return user;
    }

    public User getUserByUsernameAndPassword(String username, String password) {
        List<User> user = userRepository.findByUsernameAndPassword(username, password);

        if (user.isEmpty()) {
            return null;
        }

        return user.getFirst();
    }

    public User getUserByUsername(String username) {
        List<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            return null;
        }

        return user.getFirst();
    }

    public User getUserByEmail(String email) {
        List<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            return null;
        }

        return user.getFirst();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }
}
