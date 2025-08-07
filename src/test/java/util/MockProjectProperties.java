package util;

import org.academiadecodigo.wordsgame.game.ProjectProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Test utility mock implementation of ProjectProperties with injectable map.
 * Allows complete control over property values in tests without static mocking.
 * Provides the same interface as ProjectProperties but with injectable properties.
 */
public class MockProjectProperties {
    
    private final Map<String, String> testProperties;
    
    public MockProjectProperties() {
        this.testProperties = new HashMap<>();
        setDefaultTestProperties();
    }
    
    public MockProjectProperties(Map<String, String> properties) {
        this.testProperties = new HashMap<>(properties);
    }
    
    public String getProperty(String propertyName) {
        return testProperties.get(propertyName);
    }
    
    // Test utility methods
    public void setProperty(String key, String value) {
        testProperties.put(key, value);
    }
    
    public void removeProperty(String key) {
        testProperties.remove(key);
    }
    
    public void clearProperties() {
        testProperties.clear();
    }
    
    public void addProperties(Map<String, String> properties) {
        testProperties.putAll(properties);
    }
    
    public Map<String, String> getAllProperties() {
        return new HashMap<>(testProperties);
    }
    
    public boolean hasProperty(String key) {
        return testProperties.containsKey(key);
    }
    
    private void setDefaultTestProperties() {
        testProperties.put("admin.name", "testAdmin");
        testProperties.put("admin.password", "testPassword");
        testProperties.put("server.grid.rows.number", "5");
        testProperties.put("server.grid.score.0", "10");
        testProperties.put("server.grid.score.1", "25");
        testProperties.put("server.grid.score.2", "50");
        testProperties.put("server.grid.score.3", "100");
        testProperties.put("server.grid.score.4", "200");
        testProperties.put("server.grid.score.5", "500");
    }
    
    // Static factory methods for common test scenarios
    public static MockProjectProperties withDefaults() {
        return new MockProjectProperties();
    }
    
    public static MockProjectProperties withScores(int... scores) {
        MockProjectProperties mock = new MockProjectProperties();
        mock.clearProperties();
        
        for (int i = 0; i < scores.length; i++) {
            mock.setProperty("server.grid.score." + i, String.valueOf(scores[i]));
        }
        
        return mock;
    }
    
    public static MockProjectProperties withAdminCredentials(String username, String password) {
        MockProjectProperties mock = new MockProjectProperties();
        mock.setProperty("admin.name", username);
        mock.setProperty("admin.password", password);
        return mock;
    }
    
    public static MockProjectProperties withGridRows(int rows) {
        MockProjectProperties mock = new MockProjectProperties();
        mock.setProperty("server.grid.rows.number", String.valueOf(rows));
        return mock;
    }
    
    public static MockProjectProperties empty() {
        MockProjectProperties mock = new MockProjectProperties();
        mock.clearProperties();
        return mock;
    }
    
    public static MockProjectProperties withCustomProperties(Map<String, String> properties) {
        return new MockProjectProperties(properties);
    }
    
    // Builder pattern for fluent test setup
    public static class Builder {
        private final Map<String, String> properties = new HashMap<>();
        
        public Builder withProperty(String key, String value) {
            properties.put(key, value);
            return this;
        }
        
        public Builder withAdminCredentials(String username, String password) {
            properties.put("admin.name", username);
            properties.put("admin.password", password);
            return this;
        }
        
        public Builder withScore(int index, int score) {
            properties.put("server.grid.score." + index, String.valueOf(score));
            return this;
        }
        
        public Builder withGridRows(int rows) {
            properties.put("server.grid.rows.number", String.valueOf(rows));
            return this;
        }
        
        public MockProjectProperties build() {
            return new MockProjectProperties(properties);
        }
    }
    
    public static Builder builder() {
        return new Builder();
    }
}
