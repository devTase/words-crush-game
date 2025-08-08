package util;

import static org.mockito.Mockito.mock;

import java.net.Socket;
import org.academiadecodigo.wordsgame.application.server.ClientDispatch;
import org.academiadecodigo.wordsgame.entities.users.Admin;
import org.academiadecodigo.wordsgame.entities.users.Player;
import org.academiadecodigo.wordsgame.game.stages.Stage;

/**
 * Test utility Builder pattern for creating User objects with sensible defaults.
 * Reduces duplication in test setup code.
 */
public class UserBuilder {

    private int id = 1;
    private String userName = "testUser";
    private int score = 0;
    private int lives = 3;
    private boolean isReady = false;
    private ClientDispatch clientDispatch;
    private Socket socket;
    private Stage actualStage;
    private boolean isReadyConfirmed = false;
    private boolean isKicked = false;

    public UserBuilder() {
        // Set mock defaults
        this.clientDispatch = mock(ClientDispatch.class);
        this.socket = mock(Socket.class);
        this.actualStage = mock(Stage.class);
    }

    public UserBuilder withId(int id) {
        this.id = id;
        return this;
    }

    public UserBuilder withUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public UserBuilder withScore(int score) {
        this.score = score;
        return this;
    }

    public UserBuilder withLives(int lives) {
        this.lives = lives;
        return this;
    }

    public UserBuilder withReady(boolean isReady) {
        this.isReady = isReady;
        return this;
    }

    public UserBuilder withClientDispatch(ClientDispatch clientDispatch) {
        this.clientDispatch = clientDispatch;
        return this;
    }

    public UserBuilder withSocket(Socket socket) {
        this.socket = socket;
        return this;
    }

    public UserBuilder withActualStage(Stage actualStage) {
        this.actualStage = actualStage;
        return this;
    }

    public UserBuilder withReadyConfirmed(boolean isReadyConfirmed) {
        this.isReadyConfirmed = isReadyConfirmed;
        return this;
    }

    public UserBuilder withKicked(boolean isKicked) {
        this.isKicked = isKicked;
        return this;
    }

    public Player buildPlayer() {
        return new Player(
                id, userName, score, lives, isReady, clientDispatch, socket, actualStage, isKicked, isReadyConfirmed);
    }

    public Admin buildAdmin() {
        return new Admin(id, userName, score, lives, isReady, clientDispatch, socket, actualStage, isReadyConfirmed);
    }

    // Static factory methods for common scenarios
    public static UserBuilder defaultPlayer() {
        return new UserBuilder();
    }

    public static UserBuilder defaultAdmin() {
        return new UserBuilder().withUserName("adminUser");
    }

    public static UserBuilder kickedPlayer() {
        return new UserBuilder().withKicked(true);
    }

    public static UserBuilder readyPlayer() {
        return new UserBuilder().withReady(true).withReadyConfirmed(true);
    }

    public static UserBuilder highScorePlayer() {
        return new UserBuilder().withScore(1000).withLives(1);
    }
}
