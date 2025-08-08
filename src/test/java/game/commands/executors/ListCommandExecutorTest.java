package game.commands.executors;

import org.academiadecodigo.wordsgame.entities.users.User;
import org.academiadecodigo.wordsgame.game.ChatCommandsMessagesTrafficManager;
import org.academiadecodigo.wordsgame.game.commands.executors.ListCommandExecutor;
import org.academiadecodigo.wordsgame.misc.Messages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ListCommandExecutorTest {

    @Mock
    private User mockUser1;
    
    @Mock
    private User mockUser2;
    
    @Mock
    private User mockUser3;

    private ListCommandExecutor listCommandExecutor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        listCommandExecutor = new ListCommandExecutor();
    }

    @Nested
    @DisplayName("Is Applicable Tests")
    class IsApplicableTests {

        @Test
        @DisplayName("Should return true when command is /list")
        void isApplicable_should_return_true_when_command_is_list() {
            // Given
            String listCommand = "/list";
            
            // When
            boolean result = listCommandExecutor.isApplicable(listCommand);
            
            // Then
            assertTrue(result);
        }

        @Test
        @DisplayName("Should return false when command is not /list")
        void isApplicable_should_return_false_when_command_is_not_list() {
            // Given
            String otherCommand = "/start";
            
            // When
            boolean result = listCommandExecutor.isApplicable(otherCommand);
            
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
                listCommandExecutor.isApplicable(nullCommand);
            });
        }

        @Test
        @DisplayName("Should return false when command is empty")
        void isApplicable_should_return_false_when_command_is_empty() {
            // Given
            String emptyCommand = "";
            
            // When
            boolean result = listCommandExecutor.isApplicable(emptyCommand);
            
            // Then
            assertFalse(result);
        }
    }

    @Nested
    @DisplayName("Execute Valid Command Tests")
    class ExecuteValidCommandTests {

        @Test
        @DisplayName("Should return formatted users list and notify traffic manager")
        void executeValidCommand_should_return_formatted_users_list_and_notify_traffic_manager() {
            // Given
            String listCommand = "/list";
            String headerMessage = "User's List";
            String notificationMessage = "[INFO] %s is watching player's list";
            
            when(mockUser1.getUserName()).thenReturn("Player1");
            when(mockUser1.isReady()).thenReturn(true);
            when(mockUser2.getUserName()).thenReturn("Player2");
            when(mockUser2.isReady()).thenReturn(false);
            when(mockUser3.getUserName()).thenReturn("Admin1");
            when(mockUser3.isReady()).thenReturn(true);
            
            List<User> usersList = new ArrayList<>();
            usersList.add(mockUser1);
            usersList.add(mockUser2);
            usersList.add(mockUser3);
            
            try (MockedStatic<Messages> mockedMessages = mockStatic(Messages.class);
                 MockedStatic<ChatCommandsMessagesTrafficManager> mockedTrafficManager = 
                     mockStatic(ChatCommandsMessagesTrafficManager.class)) {
                
                mockedMessages.when(() -> Messages.getMessage("INFO_LIST_PLAYERS"))
                    .thenReturn(headerMessage);
                mockedMessages.when(() -> Messages.getMessage("INFO_SOMEONE_IS_WATCHING_LIST"))
                    .thenReturn(notificationMessage);
                
                // When
                String result = listCommandExecutor.execute(listCommand, mockUser1, usersList);
                
                // Then
                assertTrue(result.contains(headerMessage));
                assertTrue(result.contains("> Player1 (true)"));
                assertTrue(result.contains("> Player2 (false)"));
                assertTrue(result.contains("> Admin1 (true)"));
                
                mockedMessages.verify(() -> Messages.getMessage("INFO_LIST_PLAYERS"));
                mockedMessages.verify(() -> Messages.getMessage("INFO_SOMEONE_IS_WATCHING_LIST"));
                mockedTrafficManager.verify(() -> 
                    ChatCommandsMessagesTrafficManager.sendMessageToServer(anyString()));
            }
        }

        @Test
        @DisplayName("Should handle empty users list")
        void executeValidCommand_should_handle_empty_users_list() {
            // Given
            String listCommand = "/list";
            String headerMessage = "User's List";
            String notificationMessage = "[INFO] %s is watching player's list";
            
            List<User> emptyUsersList = new ArrayList<>();
            
            try (MockedStatic<Messages> mockedMessages = mockStatic(Messages.class);
                 MockedStatic<ChatCommandsMessagesTrafficManager> mockedTrafficManager = 
                     mockStatic(ChatCommandsMessagesTrafficManager.class)) {
                
                mockedMessages.when(() -> Messages.getMessage("INFO_LIST_PLAYERS"))
                    .thenReturn(headerMessage);
                mockedMessages.when(() -> Messages.getMessage("INFO_SOMEONE_IS_WATCHING_LIST"))
                    .thenReturn(notificationMessage);
                
                when(mockUser1.getUserName()).thenReturn("TestUser");
                
                // When
                String result = listCommandExecutor.execute(listCommand, mockUser1, emptyUsersList);
                
                // Then
                assertEquals(headerMessage, result);
                
                mockedMessages.verify(() -> Messages.getMessage("INFO_LIST_PLAYERS"));
                mockedMessages.verify(() -> Messages.getMessage("INFO_SOMEONE_IS_WATCHING_LIST"));
                mockedTrafficManager.verify(() -> 
                    ChatCommandsMessagesTrafficManager.sendMessageToServer(anyString()));
            }
        }

        @Test
        @DisplayName("Should handle traffic manager mock properly")
        void executeValidCommand_should_handle_traffic_manager_mock_properly() {
            // Given
            String listCommand = "/list";
            String headerMessage = "User's List";
            String notificationMessage = "[INFO] %s is watching player's list";
            String userName = "TestUser";
            
            when(mockUser1.getUserName()).thenReturn("Player1");
            when(mockUser1.isReady()).thenReturn(true);
            
            List<User> usersList = new ArrayList<>();
            usersList.add(mockUser1);
            
            try (MockedStatic<Messages> mockedMessages = mockStatic(Messages.class);
                 MockedStatic<ChatCommandsMessagesTrafficManager> mockedTrafficManager = 
                     mockStatic(ChatCommandsMessagesTrafficManager.class)) {
                
                mockedMessages.when(() -> Messages.getMessage("INFO_LIST_PLAYERS"))
                    .thenReturn(headerMessage);
                mockedMessages.when(() -> Messages.getMessage("INFO_SOMEONE_IS_WATCHING_LIST"))
                    .thenReturn(notificationMessage);
                
                when(mockUser2.getUserName()).thenReturn(userName);
                
                // When
                String result = listCommandExecutor.execute(listCommand, mockUser2, usersList);
                
                // Then
                assertNotNull(result);
                assertTrue(result.contains(headerMessage));
                
                mockedTrafficManager.verify(() -> 
                    ChatCommandsMessagesTrafficManager.sendMessageToServer(
                        String.format(notificationMessage, userName)), times(1));
            }
        }
    }
}
