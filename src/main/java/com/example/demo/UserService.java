package com.example.demo;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    
    private User currentUser;

    public User getCurrentUser() {
        // Mock implementation, replace with actual user fetching logic
        if (currentUser == null) {
            currentUser = new User();
            currentUser.setUsername("ExampleUser");
            currentUser.setRole("Grower"); // Default role for mock user
            currentUser.setBalance(100.00);
            currentUser.setPosts(10);
            currentUser.setPlants(25);
            currentUser.setProfileImage("/path/to/profile.jpg");
        }
        return currentUser;
    }

    public void setCurrentUserRole(String role) {
        // I am assuming currentUser is being managed here 
        if (currentUser != null) {
            currentUser.setRole(role);
        }
    }

    public boolean isRoleSet() {
        return currentUser != null && currentUser.getRole() != null;
    }

    public String getCurrentUserRole() {
        return currentUser != null ? currentUser.getRole() : null;
    }
    // Additional methods can include fetching user by ID, updating user details, etc.
}
