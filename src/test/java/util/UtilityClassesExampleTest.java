package util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import org.academiadecodigo.wordsgame.entities.users.Admin;
import org.academiadecodigo.wordsgame.entities.users.Player;
import org.academiadecodigo.wordsgame.game.stages.Stage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Example test demonstrating how to use the utility classes to reduce duplication.
 * This shows the before/after approach for cleaner test setup.
 */
public class UtilityClassesExampleTest {

    @Test
    @DisplayName("Example: DummySocket usage for controllable socket behavior")
    void dummySocket_example_usage() throws IOException {
        // given - socket that should throw on close
        DummySocket socket = new DummySocket(true);

        // when & then
        assertThrows(IOException.class, () -> socket.close());
        assertTrue(socket.getShouldThrowOnClose());

        // given - socket that closes normally
        DummySocket normalSocket = new DummySocket();

        // when
        normalSocket.close();

        // then
        assertTrue(normalSocket.isClosed());
        assertFalse(normalSocket.getShouldThrowOnClose());
    }

    @Test
    @DisplayName("Example: UserBuilder usage for clean player creation")
    void userBuilder_example_usage() {
        // Before: Verbose setup with many parameters
        // Player player = new Player(1, "testUser", 0, 3, false, mockClientDispatch, mockSocket, mockStage, false,
        // false);

        // After: Clean, readable setup with builder
        Player defaultPlayer = UserBuilder.defaultPlayer().buildPlayer();
        Player customPlayer = UserBuilder.defaultPlayer()
                .withUserName("customUser")
                .withScore(100)
                .withLives(1)
                .withReady(true)
                .buildPlayer();

        Player kickedPlayer = UserBuilder.kickedPlayer().buildPlayer();
        Admin admin = UserBuilder.defaultAdmin().buildAdmin();

        // Assertions
        assertEquals("testUser", defaultPlayer.getUserName());
        assertEquals("customUser", customPlayer.getUserName());
        assertEquals(100, customPlayer.getScore());
        assertTrue(kickedPlayer.isKicked());
        assertEquals("adminUser", admin.getUserName());
    }

    @Test
    @DisplayName("Example: StageStub usage for predictable stage behavior")
    void stageStub_example_usage() {
        // Different stage scenarios with clear intent
        Stage waitingRoom = StageStub.waitingRoomStub();
        Stage gameRoom = StageStub.gameRoomStub();
        Stage fullStage = StageStub.fullStage();
        Stage throwingStage = StageStub.throwingStage(new RuntimeException("Test error"));

        // Testing behavior
        assertEquals("WaitingRoom", ((StageStub) waitingRoom).getStageName());
        assertEquals("GameRoom", ((StageStub) gameRoom).getStageName());
        assertEquals(5, fullStage.getUsersInTheRoom().size());

        // Testing exception throwing - using checkUserInput method which exists in interface
        assertThrows(RuntimeException.class, () -> throwingStage.checkUserInput(null, "test"));
    }

    @Test
    @DisplayName("Example: MockProjectProperties usage for flexible property testing")
    void mockProjectProperties_example_usage() {
        // Before: Complex static mocking setup
        // try (MockedStatic<ProjectProperties> mockedStatic = mockStatic(ProjectProperties.class)) { ... }

        // After: Simple, injectable mock
        MockProjectProperties properties = MockProjectProperties.withDefaults();
        assertEquals("testAdmin", properties.getProperty("admin.name"));

        // Custom scores for specific test
        MockProjectProperties customScores = MockProjectProperties.withScores(5, 10, 20, 40);
        assertEquals("5", customScores.getProperty("server.grid.score.0"));
        assertEquals("40", customScores.getProperty("server.grid.score.3"));

        // Fluent builder for complex setups
        MockProjectProperties builderProps = MockProjectProperties.builder()
                .withAdminCredentials("superAdmin", "secret123")
                .withGridRows(10)
                .withScore(0, 15)
                .withScore(1, 30)
                .build();

        assertEquals("superAdmin", builderProps.getProperty("admin.name"));
        assertEquals("10", builderProps.getProperty("server.grid.rows.number"));
        assertEquals("30", builderProps.getProperty("server.grid.score.1"));
    }

    @Test
    @DisplayName("Example: Combined usage for realistic test scenario")
    void combined_utility_usage_example() {
        // given - realistic test setup using all utilities
        DummySocket socket = new DummySocket();
        Stage stage = StageStub.gameRoomStub();
        MockProjectProperties properties = MockProjectProperties.withScores(10, 50, 100);

        Player player = UserBuilder.defaultPlayer()
                .withSocket(socket)
                .withActualStage(stage)
                .withScore(75)
                .buildPlayer();

        // when & then - test behavior with clean, readable setup
        assertFalse(socket.isClosed());
        assertEquals("GameRoom", ((StageStub) stage).getStageName());
        assertEquals(75, player.getScore());
        assertEquals("50", properties.getProperty("server.grid.score.1"));

        // Testing socket closure through player
        player.kick();
        assertTrue(socket.isClosed());
        assertTrue(player.isKicked());
    }
}
