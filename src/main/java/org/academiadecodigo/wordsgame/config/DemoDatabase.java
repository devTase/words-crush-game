package org.academiadecodigo.wordsgame.config;

import org.academiadecodigo.wordsgame.database.Database;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DemoDatabase extends Database {
    
    private Map<String, Map<String, String>> users;
    private boolean isConnected = false;

    public DemoDatabase() throws SQLException {
        super();
        this.users = new HashMap<>();
        // Add some demo users
        Map<String, String> adminUser = new HashMap<>();
        adminUser.put("username", "admin");
        adminUser.put("password", "admin");
        adminUser.put("role", "ROOT");
        users.put("admin", adminUser);
        
        Map<String, String> playerUser = new HashMap<>();
        playerUser.put("username", "player");
        playerUser.put("password", "player");
        playerUser.put("role", "PLAYER");
        users.put("player", playerUser);
        
        System.out.println("🎮 Demo Database initialized with in-memory data");
        System.out.println("   Available users: admin/admin (ROOT), player/player (PLAYER)");
    }

    @Override
    public void startDb() {
        isConnected = true;
        System.out.println("✅ Demo Database connected (in-memory)");
    }

    @Override
    public Connection getConnection() {
        // Return a mock connection that throws UnsupportedOperationException
        // This is just for demo purposes - in a real implementation you'd want a proper mock
        throw new UnsupportedOperationException("Demo database doesn't support direct SQL operations. Use the demo methods instead.");
    }

    @Override
    public ResultSet executeQuery(String query) {
        System.out.println("🎮 Demo: Executing query: " + query);
        // Return null as the original method does on error
        return null;
    }

    @Override
    public int executeUpdate(String query) {
        System.out.println("🎮 Demo: Executing update: " + query);
        // Return 1 to indicate success (like one row affected)
        return 1;
    }

    // Demo-specific methods
    public boolean authenticateUser(String username, String password, String expectedRole) {
        Map<String, String> user = users.get(username);
        if (user != null) {
            return user.get("password").equals(password) && user.get("role").equals(expectedRole);
        }
        return false;
    }

    public boolean loginUser(String username, String password) {
        Map<String, String> user = users.get(username);
        if (user != null) {
            return user.get("password").equals(password);
        }
        return false;
    }

    public String getUserRole(String username) {
        Map<String, String> user = users.get(username);
        return user != null ? user.get("role") : null;
    }

    public void addUser(String username, String password, String role) {
        Map<String, String> user = new HashMap<>();
        user.put("username", username);
        user.put("password", password);
        user.put("role", role);
        users.put(username, user);
        System.out.println("✅ Added user: " + username + " with role: " + role);
    }
}
