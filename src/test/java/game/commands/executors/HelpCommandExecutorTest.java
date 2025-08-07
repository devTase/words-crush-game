package game.commands.executors;

import org.academiadecodigo.wordsgame.entities.users.User;
import org.academiadecodigo.wordsgame.game.commands.executors.HelpCommandExecutor;
import org.academiadecodigo.wordsgame.game.commands.executors.list.CommandsList;
import org.academiadecodigo.wordsgame.misc.Messages;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class HelpCommandExecutorTest {

    @Mock
    private User mockUser;
    
    private HelpCommandExecutor helpCommandExecutor;
    private List<User> usersList;

    @BeforeEach
    public void setUp() {
        helpCommandExecutor = new HelpCommandExecutor();
        usersList = new ArrayList<>();
        usersList.add(mockUser);
    }

    @Nested
    @DisplayName("isApplicable method tests")
    class IsApplicableTests {

        @Test
        @DisplayName("Should return true when command is /help")
        void isApplicable_should_return_true_when_command_is_help() {
            // given
            String helpCommand = "/help";
            
            // when
            boolean result = helpCommandExecutor.isApplicable(helpCommand);
            
            // then
            assertTrue(result);
        }

        @Test
        @DisplayName("Should return false when command is not /help")
        void isApplicable_should_return_false_when_command_is_not_help() {
            // given
            String nonHelpCommand = "/start";
            
            // when
            boolean result = helpCommandExecutor.isApplicable(nonHelpCommand);
            
            // then
            assertFalse(result);
        }

        @Test
        @DisplayName("Should return false when command is empty")
        void isApplicable_should_return_false_when_command_is_empty() {
            // given
            String emptyCommand = "";
            
            // when
            boolean result = helpCommandExecutor.isApplicable(emptyCommand);
            
            // then
            assertFalse(result);
        }

        @Test
        @DisplayName("Should throw NullPointerException when command is null")
        void isApplicable_should_throw_NPE_when_command_is_null() {
            // given
            String nullCommand = null;
            
            // when & then
            assertThrows(NullPointerException.class, () -> helpCommandExecutor.isApplicable(nullCommand));
        }
    }

    @Nested
    @DisplayName("execute method tests")
    class ExecuteTests {

        @Test
        @DisplayName("Should return help text when /help command is executed")
        void execute_should_return_help_text_when_help_command_executed() {
            // given
            String helpCommand = "/help";
            String expectedHelpText = "List Of Commands: " + List.of(CommandsList.values());
            
            // when
            String result = helpCommandExecutor.execute(helpCommand, mockUser, usersList);
            
            // then
            assertEquals(expectedHelpText, result);
        }

        @Test
        @DisplayName("Should return invalid command message when non-help command is executed")
        void execute_should_return_invalid_command_message_when_non_help_command_executed() {
            // given
            String nonHelpCommand = "/invalid";
            String expectedMessage = Messages.getMessage("INFO_INVALID_COMMAND");
            
            // when
            String result = helpCommandExecutor.execute(nonHelpCommand, mockUser, usersList);
            
            // then
            assertEquals(expectedMessage, result);
        }

        @Test
        @DisplayName("Should return help text containing all available commands")
        void execute_should_return_help_text_containing_all_available_commands() {
            // given
            String helpCommand = "/help";
            
            // when
            String result = helpCommandExecutor.execute(helpCommand, mockUser, usersList);
            
            // then
            assertNotNull(result);
            assertTrue(result.contains("List Of Commands:"));
            assertTrue(result.contains(CommandsList.PM.toString()));
            assertTrue(result.contains(CommandsList.KICK.toString()));
            assertTrue(result.contains(CommandsList.HELP.toString()));
            assertTrue(result.contains(CommandsList.START.toString()));
        }
    }
}
