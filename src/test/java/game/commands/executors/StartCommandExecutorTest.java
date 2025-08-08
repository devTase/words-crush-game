package game.commands.executors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import org.academiadecodigo.wordsgame.entities.users.User;
import org.academiadecodigo.wordsgame.game.ChatCommandsMessagesTrafficManager;
import org.academiadecodigo.wordsgame.game.commands.executors.StartCommandExecutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

public class StartCommandExecutorTest {

    @Mock
    private User mockUser;

    @Mock
    private List<User> mockUsersList;

    private StartCommandExecutor startCommandExecutor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startCommandExecutor = new StartCommandExecutor();
    }

    @Nested
    @DisplayName("Is Applicable Tests")
    class IsApplicableTests {

        @Test
        @DisplayName("Should return true when command is /start")
        void isApplicable_should_return_true_when_command_is_start() {
            // Given
            String startCommand = "/start";

            // When
            boolean result = startCommandExecutor.isApplicable(startCommand);

            // Then
            assertTrue(result);
        }

        @Test
        @DisplayName("Should return false when command is not /start")
        void isApplicable_should_return_false_when_command_is_not_start() {
            // Given
            String otherCommand = "/kick";

            // When
            boolean result = startCommandExecutor.isApplicable(otherCommand);

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
                startCommandExecutor.isApplicable(nullCommand);
            });
        }

        @Test
        @DisplayName("Should return false when command is empty")
        void isApplicable_should_return_false_when_command_is_empty() {
            // Given
            String emptyCommand = "";

            // When
            boolean result = startCommandExecutor.isApplicable(emptyCommand);

            // Then
            assertFalse(result);
        }
    }

    @Nested
    @DisplayName("Execute Valid Command Tests")
    class ExecuteValidCommandTests {

        @Test
        @DisplayName("Should set user ready and return chat message from traffic manager")
        void executeValidCommand_should_set_user_ready_and_return_chat_message() {
            // Given
            String startCommand = "/start";
            String expectedChatMessage = "Game is starting...";
            List<User> usersList = new ArrayList<>();

            try (MockedStatic<ChatCommandsMessagesTrafficManager> mockedTrafficManager =
                    mockStatic(ChatCommandsMessagesTrafficManager.class)) {

                mockedTrafficManager
                        .when(ChatCommandsMessagesTrafficManager::commandStart)
                        .thenReturn(expectedChatMessage);

                // When
                String result = startCommandExecutor.execute(startCommand, mockUser, usersList);

                // Then
                assertEquals(expectedChatMessage, result);
                verify(mockUser).setReady(true);
                mockedTrafficManager.verify(ChatCommandsMessagesTrafficManager::commandStart);
            }
        }

        @Test
        @DisplayName("Should handle traffic manager mock properly")
        void executeValidCommand_should_handle_traffic_manager_mock_properly() {
            // Given
            String startCommand = "/start";
            String mockTrafficManagerResponse = "Mocked start response";
            List<User> usersList = new ArrayList<>();

            try (MockedStatic<ChatCommandsMessagesTrafficManager> mockedTrafficManager =
                    mockStatic(ChatCommandsMessagesTrafficManager.class)) {

                mockedTrafficManager
                        .when(ChatCommandsMessagesTrafficManager::commandStart)
                        .thenReturn(mockTrafficManagerResponse);

                // When
                String result = startCommandExecutor.execute(startCommand, mockUser, usersList);

                // Then
                assertEquals(mockTrafficManagerResponse, result);
                verify(mockUser).setReady(true);
                mockedTrafficManager.verify(ChatCommandsMessagesTrafficManager::commandStart, times(1));
            }
        }
    }
}
