package util;

import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;
import org.academiadecodigo.wordsgame.entities.users.User;
import org.academiadecodigo.wordsgame.game.grid.game.Grid;
import org.academiadecodigo.wordsgame.game.stages.Stage;

/**
 * Test utility Stub implementation of Stage for testing purposes.
 * Provides controllable behavior without complex mock setup.
 */
public class StageStub extends Stage {

    private String stageName;
    private boolean shouldThrowException;
    private RuntimeException exceptionToThrow;

    public StageStub() {
        super(mock(Grid.class), 10, new ArrayList<>());
        this.stageName = "TestStage";
        this.shouldThrowException = false;
    }

    public StageStub(String stageName) {
        super(mock(Grid.class), 10, new ArrayList<>());
        this.stageName = stageName;
        this.shouldThrowException = false;
    }

    public StageStub(Grid grid, int maxPlayers, List<User> usersInTheRoom) {
        super(grid, maxPlayers, usersInTheRoom);
        this.stageName = "TestStage";
        this.shouldThrowException = false;
    }

    public StageStub(String stageName, Grid grid, int maxPlayers, List<User> usersInTheRoom) {
        super(grid, maxPlayers, usersInTheRoom);
        this.stageName = stageName;
        this.shouldThrowException = false;
    }

    @Override
    public void run() {
        if (shouldThrowException && exceptionToThrow != null) {
            throw exceptionToThrow;
        }
        // Stub implementation - does nothing
    }

    @Override
    public void checkUserInput(User user, String message) {
        if (shouldThrowException && exceptionToThrow != null) {
            throw exceptionToThrow;
        }
        // Stub implementation - does nothing
    }

    public void start() {
        if (shouldThrowException && exceptionToThrow != null) {
            throw exceptionToThrow;
        }
        // Stub implementation - does nothing
    }

    public void close() {
        if (shouldThrowException && exceptionToThrow != null) {
            throw exceptionToThrow;
        }
        // Stub implementation - does nothing
    }

    @Override
    public void playerLost(User user) {
        if (shouldThrowException && exceptionToThrow != null) {
            throw exceptionToThrow;
        }
        // Stub implementation - removes user from room
        getUsersInTheRoom().remove(user);
    }

    // Test control methods
    public void setShouldThrowException(boolean shouldThrow) {
        this.shouldThrowException = shouldThrow;
    }

    public void setExceptionToThrow(RuntimeException exception) {
        this.exceptionToThrow = exception;
        this.shouldThrowException = exception != null;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public boolean getShouldThrowException() {
        return shouldThrowException;
    }

    // Static factory methods for common test scenarios
    public static StageStub waitingRoomStub() {
        return new StageStub("WaitingRoom");
    }

    public static StageStub gameRoomStub() {
        return new StageStub("GameRoom");
    }

    public static StageStub finishRoomStub() {
        return new StageStub("FinishRoom");
    }

    public static StageStub fullStage() {
        List<User> users = new ArrayList<>();
        // Add some dummy users for testing
        for (int i = 0; i < 5; i++) {
            users.add(UserBuilder.defaultPlayer()
                    .withId(i)
                    .withUserName("user" + i)
                    .buildPlayer());
        }
        return new StageStub("FullStage", mock(Grid.class), 5, users);
    }

    public static StageStub emptyStage() {
        return new StageStub("EmptyStage", mock(Grid.class), 10, new ArrayList<>());
    }

    public static StageStub throwingStage(RuntimeException exception) {
        StageStub stub = new StageStub("ThrowingStage");
        stub.setExceptionToThrow(exception);
        return stub;
    }
}
