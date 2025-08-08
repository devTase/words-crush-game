package org.academiadecodigo.wordsgame.config;

import jakarta.inject.Inject;
import java.sql.SQLException;
import java.util.Map;
import org.academiadecodigo.wordsgame.entities.users.User;
import org.academiadecodigo.wordsgame.service.UserService;

public class DemoUserService extends UserService {

    private final DemoDatabase demoDatabase;

    @Inject
    public DemoUserService(DemoDatabase demoDatabase) {
        super(demoDatabase); // Call parent constructor
        this.demoDatabase = demoDatabase;
    }

    @Override
    public Map<String, String> getUserById(int id) throws SQLException {
        // For demo purposes, return a dummy user
        return Map.of("demo_user", "demo_password");
    }

    @Override
    public void createNewUser() {
        System.out.println("🎮 Demo: Creating new user...");
    }

    @Override
    public void getUsers() {
        System.out.println("🎮 Demo: Retrieving users list...");
    }

    @Override
    public void saveUser(User user) {
        System.out.println(
                "🎮 Demo: Saving user: " + (user != null ? user.getClass().getSimpleName() : "null"));
    }

    @Override
    public void deleteUser(int id) {
        System.out.println("🎮 Demo: Deleting user with id: " + id);
    }

    @Override
    public void saveScore(int score) {
        System.out.println("🎮 Demo: Saving score: " + score);
    }

    @Override
    public int getScore() {
        System.out.println("🎮 Demo: Getting score...");
        return 1337; // Demo score
    }
}
