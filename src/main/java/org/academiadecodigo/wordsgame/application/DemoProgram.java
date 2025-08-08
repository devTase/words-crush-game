package org.academiadecodigo.wordsgame.application;

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.util.Scanner;
import org.academiadecodigo.wordsgame.application.server.GameServer;
import org.academiadecodigo.wordsgame.config.DemoGameModule;

public class DemoProgram {

    public static void main(String[] args) {
        System.out.println("🎮 ===============================================");
        System.out.println("🎮    WORDS CRUSH GAME - DEMO MODE");
        System.out.println("🎮 ===============================================");
        System.out.println("🎮 Starting game server in demo mode...");
        System.out.println("🎮 (No MySQL database required!)");
        System.out.println();

        try {
            // Create Guice injector with our demo module
            System.out.println("🔧 Initializing Dependency Injection...");
            Injector injector = Guice.createInjector(new DemoGameModule());

            // Get the GameServer instance with all dependencies injected
            System.out.println("🏗️  Creating Game Server instance...");
            GameServer server = injector.getInstance(GameServer.class);

            System.out.println();
            System.out.println("✅ Game Server successfully started!");
            System.out.println("🌐 Server is listening for connections...");
            System.out.println();
            System.out.println("📋 DEMO FEATURES:");
            System.out.println("   - In-memory user database");
            System.out.println("   - Pre-configured demo users:");
            System.out.println("     * admin/admin (ROOT access)");
            System.out.println("     * player/player (PLAYER access)");
            System.out.println("   - Network server ready for client connections");
            System.out.println();
            System.out.println("🚀 To connect a client:");
            System.out.println(
                    "   mvn exec:java -Dexec.mainClass=\"org.academiadecodigo.wordsgame.application.client.Client\"");
            System.out.println();
            System.out.println("⏹️  Press ENTER to stop the server...");

            // Start the server in a separate thread
            Thread serverThread = new Thread(() -> {
                try {
                    server.manageNewConnections();
                } catch (Exception e) {
                    System.err.println("❌ Server error: " + e.getMessage());
                }
            });
            serverThread.start();

            // Wait for user input to stop
            Scanner scanner = new Scanner(System.in);
            try {
                scanner.nextLine();
            } catch (java.util.NoSuchElementException e) {
                // Handle case when no input is available (e.g., when run via script)
                System.out.println("🎮 Server running in background mode...");
                System.out.println("🛑 To stop the server, press Ctrl+C");

                // Keep server running indefinitely when no input available
                try {
                    Thread.currentThread().join();
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }

            System.out.println();
            System.out.println("🛑 Shutting down server...");
            server.close();
            serverThread.interrupt();
            System.out.println("✅ Server stopped successfully!");
            System.out.println("👋 Thanks for trying the Words Crush Game demo!");

        } catch (Exception e) {
            System.err.println("❌ Failed to start game server: " + e.getMessage());
            System.err.println("🔍 Error details:");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
