package game.commands;

import org.academiadecodigo.wordsgame.entities.users.User;
import org.academiadecodigo.wordsgame.game.commands.CommandRunner;
import org.academiadecodigo.wordsgame.game.commands.executors.CommandExecutor;
import org.academiadecodigo.wordsgame.misc.Messages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CommandRunnerTest {

    @Mock
    private CommandExecutor mockExecutor1;
    
    @Mock
    private CommandExecutor mockExecutor2;
    
    @Mock
    private User mockUser;
    
    @Mock
    private List<User> mockUsersList;

    private CommandRunner commandRunner;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        commandRunner = new CommandRunner(Arrays.asList(mockExecutor1, mockExecutor2));
    }

    @Nested
    @DisplayName("Blank Command Tests")
    class BlankCommandTests {

        @Test
        @DisplayName("Should return INFO_INVALIDBLANKS when command is empty string")
        void runCommand_should_return_invalid_blanks_message_when_command_is_empty() {
            // Given
            String emptyCommand = "";
            
            // When
            String result = commandRunner.runCommand(emptyCommand, mockUser, mockUsersList);
            
            // Then
            assertEquals(Messages.getMessage("INFO_INVALIDBLANKS"), result);
            verifyNoInteractions(mockExecutor1, mockExecutor2);
        }

        @Test
        @DisplayName("Should return INFO_INVALIDBLANKS when command is blank spaces")
        void runCommand_should_return_invalid_blanks_message_when_command_is_blank() {
            // Given
            String blankCommand = "   ";
            
            // When
            String result = commandRunner.runCommand(blankCommand, mockUser, mockUsersList);
            
            // Then
            assertEquals(Messages.getMessage("INFO_INVALIDBLANKS"), result);
            verifyNoInteractions(mockExecutor1, mockExecutor2);
        }
    }

    @Nested
    @DisplayName("Unknown Command Tests")
    class UnknownCommandTests {

        @Test
        @DisplayName("Should return INFO_INVALID_COMMAND when no executor applies")
        void runCommand_should_return_invalid_command_message_when_no_executor_matches() {
            // Given
            String unknownCommand = "/unknown";
            when(mockExecutor1.isApplicable(unknownCommand)).thenReturn(false);
            when(mockExecutor2.isApplicable(unknownCommand)).thenReturn(false);
            
            // When
            String result = commandRunner.runCommand(unknownCommand, mockUser, mockUsersList);
            
            // Then
            assertEquals(Messages.getMessage("INFO_INVALID_COMMAND"), result);
            verify(mockExecutor1).isApplicable(unknownCommand);
            verify(mockExecutor2).isApplicable(unknownCommand);
            verifyNoMoreInteractions(mockExecutor1, mockExecutor2);
        }
    }

    @Nested
    @DisplayName("Valid Command Delegation Tests")
    class ValidCommandDelegationTests {

        @Test
        @DisplayName("Should delegate to first matching executor")
        void runCommand_should_delegate_to_first_matching_executor() {
            // Given
            String validCommand = "/start";
            String expectedResult = "Command executed successfully";
            
            when(mockExecutor1.isApplicable(validCommand)).thenReturn(true);
            when(mockExecutor1.execute(validCommand, mockUser, mockUsersList)).thenReturn(expectedResult);
            
            // When
            String result = commandRunner.runCommand(validCommand, mockUser, mockUsersList);
            
            // Then
            assertEquals(expectedResult, result);
            verify(mockExecutor1).isApplicable(validCommand);
            verify(mockExecutor1).execute(validCommand, mockUser, mockUsersList);
            verifyNoInteractions(mockExecutor2);
        }

        @Test
        @DisplayName("Should delegate to second executor when first doesn't match")
        void runCommand_should_delegate_to_second_executor_when_first_does_not_match() {
            // Given
            String validCommand = "/kick";
            String expectedResult = "Player kicked";
            
            when(mockExecutor1.isApplicable(validCommand)).thenReturn(false);
            when(mockExecutor2.isApplicable(validCommand)).thenReturn(true);
            when(mockExecutor2.execute(validCommand, mockUser, mockUsersList)).thenReturn(expectedResult);
            
            // When
            String result = commandRunner.runCommand(validCommand, mockUser, mockUsersList);
            
            // Then
            assertEquals(expectedResult, result);
            verify(mockExecutor1).isApplicable(validCommand);
            verify(mockExecutor2).isApplicable(validCommand);
            verify(mockExecutor2).execute(validCommand, mockUser, mockUsersList);
            verifyNoMoreInteractions(mockExecutor1);
        }
    }
}
