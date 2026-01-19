package game.commands.executors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import org.academiadecodigo.wordsgame.entities.users.Admin;
import org.academiadecodigo.wordsgame.entities.users.Player;
import org.academiadecodigo.wordsgame.entities.users.User;
import org.academiadecodigo.wordsgame.game.ChatCommandsMessagesTrafficManager;
import org.academiadecodigo.wordsgame.game.commands.executors.KickCommandExecutor;
import org.academiadecodigo.wordsgame.misc.Messages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

public class KickCommandExecutorTest {

    @Mock
    private Admin mockAdmin;

    @Mock
    private Player mockPlayer;

    @Mock
    private List<User> mockUsersList;

    private KickCommandExecutor kickCommandExecutor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        kickCommandExecutor = new KickCommandExecutor();
    }

    @Nested
    @DisplayName("Is Applicable Tests")
    class IsApplicableTests {

        @Test
        @DisplayName("Should return true when command is /kick")
        void isApplicable_should_return_true_when_command_is_kick() {
            // Given
            String kickCommand = "/kick";

            // When
            boolean result = kickCommandExecutor.isApplicable(kickCommand);

            // Then
            assertTrue(result);
        }

        @Test
        @DisplayName("Should return false when command is not /kick")
        void isApplicable_should_return_false_when_command_is_not_kick() {
            // Given
            String otherCommand = "/start";

            // When
            boolean result = kickCommandExecutor.isApplicable(otherCommand);

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
                kickCommandExecutor.isApplicable(nullCommand);
            });
        }

        @Test
        @DisplayName("Should return false when command is empty")
        void isApplicable_should_return_false_when_command_is_empty() {
            // Given
            String emptyCommand = "";

            // When
            boolean result = kickCommandExecutor.isApplicable(emptyCommand);

            // Then
            assertFalse(result);
        }
    }

    @Nested
    @DisplayName("Execute Valid Command Tests")
    class ExecuteValidCommandTests {

        @Test
        @DisplayName("Should return chat message when user is Admin")
        void executeValidCommand_should_return_chat_message_when_user_is_admin() {
            // Given
            String kickCommand = "/kick";
            String expectedChatMessage = "Select player to kick";
            List<User> usersList = new ArrayList<>();

            try (MockedStatic<ChatCommandsMessagesTrafficManager> mockedTrafficManager =
                    mockStatic(ChatCommandsMessagesTrafficManager.class)) {

                mockedTrafficManager
                        .when(() -> ChatCommandsMessagesTrafficManager.commandKick(mockAdmin, usersList))
                        .thenReturn(expectedChatMessage);

                // When
                String result = kickCommandExecutor.execute(kickCommand, mockAdmin, usersList);

                // Then
                assertEquals(expectedChatMessage, result);
                mockedTrafficManager.verify(() -> ChatCommandsMessagesTrafficManager.commandKick(mockAdmin, usersList));
            }
        }

        @Test
        @DisplayName("Should return insufficient rights message when user is not Admin")
        void executeValidCommand_should_return_insufficient_rights_message_when_user_is_not_admin() {
            // Given
            String kickCommand = "/kick";
            String expectedMessage = "[INFO] You don't have admin rights to do this";
            List<User> usersList = new ArrayList<>();

            try (MockedStatic<Messages> mockedMessages = mockStatic(Messages.class)) {
                mockedMessages
                        .when(() -> Messages.getMessage("INFO_INSUFFICIENT_ADMIN_RIGHTS"))
                        .thenReturn(expectedMessage);

                // When
                String result = kickCommandExecutor.execute(kickCommand, mockPlayer, usersList);

                // Then
                assertEquals(expectedMessage, result);
                mockedMessages.verify(() -> Messages.getMessage("INFO_INSUFFICIENT_ADMIN_RIGHTS"));
            }
        }

        @Test
        @DisplayName("Should handle traffic manager mock properly for admin")
        void executeValidCommand_should_handle_traffic_manager_mock_properly_for_admin() {
            // Given
            String kickCommand = "/kick";
            String mockTrafficManagerResponse = "Mocked kick response";
            List<User> usersList = new ArrayList<>();

            try (MockedStatic<ChatCommandsMessagesTrafficManager> mockedTrafficManager =
                    mockStatic(ChatCommandsMessagesTrafficManager.class)) {

                mockedTrafficManager
                        .when(() -> ChatCommandsMessagesTrafficManager.commandKick(mockAdmin, usersList))
                        .thenReturn(mockTrafficManagerResponse);

                // When
                String result = kickCommandExecutor.execute(kickCommand, mockAdmin, usersList);

                // Then
                assertEquals(mockTrafficManagerResponse, result);
                mockedTrafficManager.verify(
                        () -> ChatCommandsMessagesTrafficManager.commandKick(mockAdmin, usersList), times(1));
            }
        }
    }
}
