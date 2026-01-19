package game.commands.executors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import org.academiadecodigo.wordsgame.entities.users.User;
import org.academiadecodigo.wordsgame.game.ChatCommandsMessagesTrafficManager;
import org.academiadecodigo.wordsgame.game.PlayersUpdateBroadcaster;
import org.academiadecodigo.wordsgame.game.commands.executors.ReadyCommandExecutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

public class ReadyCommandExecutorTest {

    @Mock
    private User mockUser;

    private ReadyCommandExecutor readyCommandExecutor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        readyCommandExecutor = new ReadyCommandExecutor();
    }

    @Nested
    @DisplayName("Is Applicable Tests")
    class IsApplicableTests {

        @Test
        @DisplayName("Should return true when command is /ready")
        void isApplicable_should_return_true_when_command_is_ready() {
            // Given
            String readyCommand = "/ready";

            // When
            boolean result = readyCommandExecutor.isApplicable(readyCommand);

            // Then
            assertTrue(result);
        }

        @Test
        @DisplayName("Should return false when command is not /ready")
        void isApplicable_should_return_false_when_command_is_not_ready() {
            // Given
            String otherCommand = "/start";

            // When
            boolean result = readyCommandExecutor.isApplicable(otherCommand);

            // Then
            assertFalse(result);
        }

        @Test
        @DisplayName("Should throw NullPointerException when command is null")
        void isApplicable_should_throw_null_pointer_exception_when_command_is_null() {
            // Given
            String nullCommand = null;

            // When & Then
            assertThrows(NullPointerException.class, () -> {
                readyCommandExecutor.isApplicable(nullCommand);
            });
        }

        @Test
        @DisplayName("Should return false when command is empty")
        void isApplicable_should_return_false_when_command_is_empty() {
            // Given
            String emptyCommand = "";

            // When
            boolean result = readyCommandExecutor.isApplicable(emptyCommand);

            // Then
            assertFalse(result);
        }
    }

    @Nested
    @DisplayName("Execute Valid Command Tests")
    class ExecuteValidCommandTests {

        @Test
        @DisplayName("Should set user as ready and return confirmation message")
        void executeValidCommand_should_set_user_ready_and_return_confirmation() {
            // Given
            String readyCommand = "/ready";
            String expectedMessage = "[INFO] You are ready to start";
            List<User> usersList = new ArrayList<>();
            usersList.add(mockUser);

            try (MockedStatic<ChatCommandsMessagesTrafficManager> mockedTrafficManager =
                            mockStatic(ChatCommandsMessagesTrafficManager.class);
                    MockedStatic<PlayersUpdateBroadcaster> mockedBroadcaster =
                            mockStatic(PlayersUpdateBroadcaster.class)) {

                mockedTrafficManager
                        .when(ChatCommandsMessagesTrafficManager::commandStart)
                        .thenReturn(expectedMessage);

                // When
                String result = readyCommandExecutor.execute(readyCommand, mockUser, usersList);

                // Then
                assertEquals(expectedMessage, result);
                verify(mockUser).setReady(true);
                mockedBroadcaster.verify(() -> PlayersUpdateBroadcaster.broadcastPlayersUpdate(usersList));
                mockedTrafficManager.verify(ChatCommandsMessagesTrafficManager::commandStart);
            }
        }

        @Test
        @DisplayName("Should call broadcastPlayersUpdate with correct user list")
        void executeValidCommand_should_broadcast_players_update() {
            // Given
            String readyCommand = "/ready";
            List<User> usersList = new ArrayList<>();
            usersList.add(mockUser);

            try (MockedStatic<ChatCommandsMessagesTrafficManager> mockedTrafficManager =
                            mockStatic(ChatCommandsMessagesTrafficManager.class);
                    MockedStatic<PlayersUpdateBroadcaster> mockedBroadcaster =
                            mockStatic(PlayersUpdateBroadcaster.class)) {

                mockedTrafficManager
                        .when(ChatCommandsMessagesTrafficManager::commandStart)
                        .thenReturn("Ready");

                // When
                readyCommandExecutor.execute(readyCommand, mockUser, usersList);

                // Then
                mockedBroadcaster.verify(() -> PlayersUpdateBroadcaster.broadcastPlayersUpdate(usersList), times(1));
            }
        }

        @Test
        @DisplayName("Should verify user setReady is called")
        void executeValidCommand_should_call_user_setReady() {
            // Given
            String readyCommand = "/ready";
            List<User> usersList = new ArrayList<>();

            try (MockedStatic<ChatCommandsMessagesTrafficManager> mockedTrafficManager =
                            mockStatic(ChatCommandsMessagesTrafficManager.class);
                    MockedStatic<PlayersUpdateBroadcaster> mockedBroadcaster =
                            mockStatic(PlayersUpdateBroadcaster.class)) {

                mockedTrafficManager
                        .when(ChatCommandsMessagesTrafficManager::commandStart)
                        .thenReturn("Ready");

                // When
                readyCommandExecutor.execute(readyCommand, mockUser, usersList);

                // Then
                verify(mockUser, times(1)).setReady(true);
            }
        }
    }
}
