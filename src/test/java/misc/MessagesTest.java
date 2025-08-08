package misc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;
import org.academiadecodigo.wordsgame.misc.Messages;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class MessagesTest {

    @ParameterizedTest
    @MethodSource("messageTestCases")
    @DisplayName("Should return expected string for given key from stubbed messages.properties")
    void getMessage_should_return_expected_string_for_given_key(String key, String expected) {
        // Given & When
        String result = Messages.getMessage(key);

        // Then
        assertEquals(expected, result);
    }

    static Stream<Arguments> messageTestCases() {
        return Stream.of(
                Arguments.of("WELCOME", "Welcome to Words Crush Game"),
                Arguments.of("INFO_SET_NICKNAME", "[INFO]: Set your nickname or login:"),
                Arguments.of("INFO_SET_PASSWORD", "[INFO]: Your password:"),
                Arguments.of("INFO_NEWCONNECTION", "[SERVER]: New Client connected: "),
                Arguments.of("INFO_SERVER_ON", "[INFO]: SERVER IS ONLINE - Waiting For playing connections"),
                Arguments.of("INFO_PORT", "[INFO]: PORT: "),
                Arguments.of("INPUT_ADMIN_PASSWORD", "[SERVER]: Insert Password: "),
                Arguments.of("INFO_INVALIDBLANKS", "[SERVER]: Blank Message not valid!"),
                Arguments.of("INFO_USERS_AVAILABLE", "[SERVER] Users Available: "),
                Arguments.of("INFO_INVALID_COMMAND", "[INFO] Invalid Command!"),
                Arguments.of("INFO_INSUFFICIENT_ADMIN_RIGHTS", "[INFO] You don't have admin rights to do this"),
                Arguments.of("QUESTION_SELECT_PLAYER_TO_KICK", "[SERVER] Select Player to Kick"),
                Arguments.of("INFO_ALL_PLAYERS_READY", "[INFO] Admin set all players Ready. Game Starting now"),
                Arguments.of("INFO_PLAYER_READY", "[INFO] You are set as Ready To Play"),
                Arguments.of(
                        "ERROR_FILE_IS_ODD", "The file your trying to read has an odd number of lines. Make it pair."),
                Arguments.of("SERVER_SCORE_DASHBOARD", "* * * LIVE PLAYER SCORES * * * "),
                Arguments.of("SERVER_SCORE_DASHBOARD_PLAYER_NAME", "Players Name::  "),
                Arguments.of("SEND_MESSAGE_TO_PLAYER", "Write your message to player:"),
                Arguments.of("DEFINE_KICK_MESSAGE", "Write a kick reason:"),
                Arguments.of("INFO_PM_SENT", "PM sent."),
                Arguments.of("YOU_KICKED_A_PLAYER", "Player kicked"),
                Arguments.of("INFO_LIST_PLAYERS", "User's List"),
                Arguments.of("ERROR_NOT_ENOUGH_PLAYERS_IN_ROOM", "[INFO] No available players in room"),
                Arguments.of("SET_YOURSELF_READY", "Welcome to Game Room. Type /ready and lets GOOO!"));
    }

    @ParameterizedTest
    @MethodSource("propertyTestCases")
    @DisplayName("Should return expected string for given property key from stubbed application.properties")
    void getProperty_should_return_expected_string_for_given_property_key(String key, String expected) {
        // Given & When
        String result = Messages.getProperty(key);

        // Then
        assertEquals(expected, result);
    }

    static Stream<Arguments> propertyTestCases() {
        return Stream.of(Arguments.of("env", "dev"), Arguments.of("server.grid.rows.number", "7"));
    }
}
