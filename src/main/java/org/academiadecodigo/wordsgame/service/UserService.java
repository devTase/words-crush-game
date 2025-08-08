package org.academiadecodigo.wordsgame.service;

import jakarta.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import org.academiadecodigo.wordsgame.database.Database;
import org.academiadecodigo.wordsgame.entities.users.User;

public class UserService {
    private final Database database;

    @Inject
    public UserService(Database database) {
        this.database = database;
    }

    public Map<String, String> getUserById(int id) throws SQLException {
        String query = "SELECT * FROM users WHERE id = " + id + ";";
        ResultSet resultSet = database.executeQuery(query);
        String userName = resultSet.getString(1);
        String password = resultSet.getString(2);

        return Map.of(userName, password);
    }

    public void createNewUser() {
        // CReate clientDispatch

        // Create User and send clientDispatch inside it
    }

    public void getUsers() {
        // implementation to retrieve list of users from database
    }

    public void saveUser(User user) {
        // implementation to save user to database
    }

    public void deleteUser(int id) {
        // implementation to delete user from database
    }

    public void saveScore(int score) {
        // TODO missing implementation
    }

    public int getScore() {
        // TODO missing implementation
        return 0;
    }
}
