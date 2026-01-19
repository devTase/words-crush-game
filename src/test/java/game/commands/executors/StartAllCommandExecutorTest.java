package game.commands.executors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import org.academiadecodigo.wordsgame.entities.users.Admin;
import org.academiadecodigo.wordsgame.entities.users.Player;
import org.academiadecodigo.wordsgame.entities.users.User;
import org.academiadecodigo.wordsgame.game.ChatCommandsMessagesTrafficManager;
import org.academiadecodigo.wordsgame.game.PlayersUpdateBroadcaster;
import org.academiadecodigo.wordsgame.game.commands.executors.StartAllCommandExecutor;
import org.academiadecodigo.wordsgame.game.stages.WaitingRoom;
import org.academiadecodigo.wordsgame.misc.Messages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

public class StartAllCommandExecutorTest {

    @Mock
    private Admin mockAdmin;

    @Mock
    private Player mockPlayer;

    private StartAllCommandExecutor startAllCommandExecutor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startAllCommandExecutor = new StartAllCommandExecutor();
    }

    @Nested
    @DisplayName("Is Applicable Tests")
    class IsApplicableTests {

        @Test
        @DisplayName("Should return true when command is /start -a")
        void isApplicable_should_return_true_when_command_is_startall() {
            // Given
            String startAllCommand = "/start -a";

            // When
            boolean result = startAllCommandExecutor.isApplicable(startAllCommand);

            // Then
            assertTrue(result);
        }

        @Test
        @DisplayName("Should return false when command is not /start -a")
        void isApplicable_should_return_false_when_command_is_not_startall() {
            // Given
            String otherCommand = "/start";

            // When
            boolean result = startAllCommandExecutor.isApplicable(otherCommand);

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
                startAllCommandExecutor.isApplicable(nullCommand);
            });
        }

        @Test
        @DisplayName("Should return false when command is empty")
        void isApplicable_should_return_false_when_command_is_empty() {
            // Given
            String emptyCommand = "";

            // When
            boolean result = startAllCommandExecutor.isApplicable(emptyCommand);

            // Then
            assertFalse(result);
        }
    }

    @Nested
    @DisplayName("Execute Valid Command Tests")
    class ExecuteValidCommandTests {

        @Test
        @DisplayName("Should set all players ready when user is Admin")
        void executeValidCommand_should_set_all_ready_when_admin() {
            // Given
            String startAllCommand = "/start -a";
            String expectedMessage = "[INFO] All players are ready";
            List<User> usersList = new ArrayList<>();
            Player player1 = mock(Player.class);
            Player player2 = mock(Player.class);
            usersList.add(mockAdmin);
            usersList.add(player1);
            usersList.add(player2);

            try (MockedStatic<ChatCommandsMessagesTrafficManager> mockedTrafficManager =
                            mockStatic(ChatCommandsMessagesTrafficManager.class);
                    MockedStatic<PlayersUpdateBroadcaster> mockedBroadcaster =
                            mockStatic(PlayersUpdateBroadcaster.class);
                    MockedStatic<WaitingRoom> mockedWaitingRoom = mockStatic(WaitingRoom.class)) {

                mockedTrafficManager
                        .when(ChatCommandsMessagesTrafficManager::commandStartAll)
                        .thenReturn(expectedMessage);

                // When
                String result = startAllCommandExecutor.execute(startAllCommand, mockAdmin, usersList);

                // Then
                assertEquals(expectedMessage, result);
                verify(mockAdmin).setReady(true);
                verify(player1).setReady(true);
                verify(player2).setReady(true);
                mockedBroadcaster.verify(() -> PlayersUpdateBroadcaster.broadcastPlayersUpdate(usersList));
                mockedWaitingRoom.verify(WaitingRoom::forceStart);
            }
        }

        @Test
        @DisplayName("Should return insufficient rights message when user is not Admin")
        void executeValidCommand_should_return_insufficient_rights_when_not_admin() {
            // Given
            String startAllCommand = "/start -a";
            String expectedMessage = "[INFO] You don't have admin rights";
            List<User> usersList = new ArrayList<>();
            usersList.add(mockPlayer);

            try (MockedStatic<Messages> mockedMessages = mockStatic(Messages.class)) {
                mockedMessages
                        .when(() -> Messages.getMessage("INFO_INSUFFICIENT_ADMIN_RIGHTS"))
                        .thenReturn(expectedMessage);

                // When
                String result = startAllCommandExecutor.execute(startAllCommand, mockPlayer, usersList);

                // Then
                assertEquals(expectedMessage, result);
                verify(mockPlayer, never()).setReady(anyBoolean());
            }
        }

        @Test
        @DisplayName("Should call WaitingRoom forceStart when admin executes")
        void executeValidCommand_should_call_force_start() {
            // Given
            String startAllCommand = "/start -a";
            List<User> usersList = new ArrayList<>();
            usersList.add(mockAdmin);

            try (MockedStatic<ChatCommandsMessagesTrafficManager> mockedTrafficManager =
                            mockStatic(ChatCommandsMessagesTrafficManager.class);
                    MockedStatic<PlayersUpdateBroadcaster> mockedBroadcaster =
                            mockStatic(PlayersUpdateBroadcaster.class);
                    MockedStatic<WaitingRoom> mockedWaitingRoom = mockStatic(WaitingRoom.class)) {

                mockedTrafficManager
                        .when(ChatCommandsMessagesTrafficManager::commandStartAll)
                        .thenReturn("Ready");

                // When
                startAllCommandExecutor.execute(startAllCommand, mockAdmin, usersList);

                // Then
                mockedWaitingRoom.verify(WaitingRoom::forceStart, times(1));
            }
        }
    }
}
