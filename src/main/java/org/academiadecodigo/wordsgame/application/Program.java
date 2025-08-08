package org.academiadecodigo.wordsgame.application;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.academiadecodigo.wordsgame.application.server.GameServer;
import org.academiadecodigo.wordsgame.config.GameModule;

import java.io.IOException;

public class Program {

    public static void main(String[] args) {
        try {
            // Create Guice injector with our module
            Injector injector = Guice.createInjector(new GameModule());
            
            // Get the GameServer instance with all dependencies injected
            GameServer server = injector.getInstance(GameServer.class);
            
            // Start accepting connections
            server.manageNewConnections();
            
        } catch (Exception e) {
            System.err.println("Failed to start game server: " + e.getMessage());
            if (e.getCause() instanceof IOException) {
                System.err.println("Check if the port is already in use or if you have permission to bind to it.");
            }
            e.printStackTrace();
            System.exit(1);
        }
    }
}
