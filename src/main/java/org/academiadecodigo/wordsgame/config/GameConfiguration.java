package org.academiadecodigo.wordsgame.config;

import javax.inject.Singleton;
import java.util.ResourceBundle;

@Singleton
public class GameConfiguration {

    private final int serverPort;
    private final int maxClients;
    private final String wordsFilePath;
    private final String environment;

    public GameConfiguration() {
        ResourceBundle appProps = ResourceBundle.getBundle("application");
        this.environment = appProps.getString("env");
        
        ResourceBundle envProps = ResourceBundle.getBundle("application-" + environment);
        this.serverPort = Integer.parseInt(envProps.getString("server.port"));
        this.maxClients = Integer.parseInt(envProps.getString("game.max-clients"));
        this.wordsFilePath = envProps.getString("game.words-file-path");
    }

    public boolean isDevelopment() {
        return "dev".equals(environment);
    }

    public boolean isProduction() {
        return "prod".equals(environment);
    }

    public boolean isTest() {
        return "test".equals(environment);
    }

    // Getters
    public int getServerPort() {
        return serverPort;
    }

    public int getMaxClients() {
        return maxClients;
    }

    public String getWordsFilePath() {
        return wordsFilePath;
    }

    public String getEnvironment() {
        return environment;
    }
}
