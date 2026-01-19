package entities.users;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import org.academiadecodigo.wordsgame.application.server.ClientDispatch;
import org.academiadecodigo.wordsgame.entities.users.Player;
import org.academiadecodigo.wordsgame.game.stages.FinishRoom;
import org.academiadecodigo.wordsgame.game.stages.GameRoom;
import org.academiadecodigo.wordsgame.game.stages.Stage;
import org.academiadecodigo.wordsgame.game.stages.WaitingRoom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserTest {

    @Mock
    private ClientDispatch mockClientDispatch;

    @Mock
    private Socket mockSocket;

    @Mock
    private WaitingRoom mockWaitingRoom;

    @Mock
    private GameRoom mockGameRoom;

    @Mock
    private FinishRoom mockFinishRoom;

    private Player testPlayer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("Constructor and Getters Tests")
    class ConstructorAndGettersTests {

        @Test
        @DisplayName("Should create user with all properties")
        void constructor_should_set_all_properties() {
            // Given & When
            testPlayer = new Player(
                    1, "TestUser", 100, 3, false, mockClientDispatch, mockSocket, mockWaitingRoom, false, false);

            // Then
            assertEquals(1, testPlayer.getId());
            assertEquals("TestUser", testPlayer.getUserName());
            assertEquals(100, testPlayer.getScore());
            assertEquals(3, testPlayer.getLives());
            assertFalse(testPlayer.isReady());
            assertFalse(testPlayer.isReadRules());
            assertEquals(mockClientDispatch, testPlayer.getClientDispatch());
            assertEquals(mockSocket, testPlayer.getSocket());
            assertEquals(mockWaitingRoom, testPlayer.getActualStage());
        }
    }

    @Nested
    @DisplayName("Setter Tests")
    class SetterTests {

        @BeforeEach
        void setUp() {
            testPlayer = new Player(
                    1, "TestUser", 0, 3, false, mockClientDispatch, mockSocket, mockWaitingRoom, false, false);
        }

        @Test
        @DisplayName("Should update user id")
        void setId_should_update_id() {
            // Given
            int newId = 999;

            // When
            testPlayer.setId(newId);

            // Then
            assertEquals(newId, testPlayer.getId());
        }

        @Test
        @DisplayName("Should update username")
        void setUserName_should_update_username() {
            // Given
            String newName = "NewName";

            // When
            testPlayer.setUserName(newName);

            // Then
            assertEquals(newName, testPlayer.getUserName());
        }

        @Test
        @DisplayName("Should update score")
        void setScore_should_update_score() {
            // Given
            int newScore = 500;

            // When
            testPlayer.setScore(newScore);

            // Then
            assertEquals(newScore, testPlayer.getScore());
        }

        @Test
        @DisplayName("Should update lives")
        void setLives_should_update_lives() {
            // Given
            int newLives = 5;

            // When
            testPlayer.setLives(newLives);

            // Then
            assertEquals(newLives, testPlayer.getLives());
        }

        @Test
        @DisplayName("Should update ready status")
        void setReady_should_update_ready_status() {
            // Given
            boolean ready = true;

            // When
            testPlayer.setReady(ready);

            // Then
            assertTrue(testPlayer.isReady());
        }

        @Test
        @DisplayName("Should update read rules status")
        void setReadRules_should_update_read_rules_status() {
            // Given
            boolean readRules = true;

            // When
            testPlayer.setReadRules(readRules);

            // Then
            assertTrue(testPlayer.isReadRules());
        }

        @Test
        @DisplayName("Should update client dispatch")
        void setClientDispatch_should_update_client_dispatch() {
            // Given
            ClientDispatch newDispatch = mock(ClientDispatch.class);

            // When
            testPlayer.setClientDispatch(newDispatch);

            // Then
            assertEquals(newDispatch, testPlayer.getClientDispatch());
        }

        @Test
        @DisplayName("Should update socket")
        void setSocket_should_update_socket() {
            // Given
            Socket newSocket = mock(Socket.class);

            // When
            testPlayer.setSocket(newSocket);

            // Then
            assertEquals(newSocket, testPlayer.getSocket());
        }

        @Test
        @DisplayName("Should update stage")
        void setActualStage_should_update_stage() {
            // Given
            Stage newStage = mock(GameRoom.class);

            // When
            testPlayer.setActualStage(newStage);

            // Then
            assertEquals(newStage, testPlayer.getActualStage());
        }
    }

    @Nested
    @DisplayName("Stage Check Tests")
    class StageCheckTests {

        @BeforeEach
        void setUp() {
            testPlayer = new Player(
                    1, "TestUser", 0, 3, false, mockClientDispatch, mockSocket, mockWaitingRoom, false, false);
        }

        @Test
        @DisplayName("Should return true when user is in waiting room")
        void isUserInWaitingRoom_should_return_true_when_in_waiting_room() {
            // Given
            testPlayer.setActualStage(mockWaitingRoom);

            // When
            boolean result = testPlayer.checkIsUserInWaitingRoom();

            // Then
            assertTrue(result);
        }

        @Test
        @DisplayName("Should return false when user is not in waiting room")
        void isUserInWaitingRoom_should_return_false_when_not_in_waiting_room() {
            // Given
            testPlayer.setActualStage(mockGameRoom);

            // When
            boolean result = testPlayer.checkIsUserInWaitingRoom();

            // Then
            assertFalse(result);
        }

        @Test
        @DisplayName("Should return true when user is in game room")
        void isUserInGameRoom_should_return_true_when_in_game_room() {
            // Given
            testPlayer.setActualStage(mockGameRoom);

            // When
            boolean result = testPlayer.checkIsUserInGameRoom();

            // Then
            assertTrue(result);
        }

        @Test
        @DisplayName("Should return false when user is not in game room")
        void isUserInGameRoom_should_return_false_when_not_in_game_room() {
            // Given
            testPlayer.setActualStage(mockWaitingRoom);

            // When
            boolean result = testPlayer.checkIsUserInGameRoom();

            // Then
            assertFalse(result);
        }

        @Test
        @DisplayName("Should return true when user is in finish stage")
        void isUserInFinishStage_should_return_true_when_in_finish_stage() {
            // Given
            testPlayer.setActualStage(mockFinishRoom);

            // When
            boolean result = testPlayer.checkIsUserInFinishSage();

            // Then
            assertTrue(result);
        }

        @Test
        @DisplayName("Should return false when user is not in finish stage")
        void isUserInFinishStage_should_return_false_when_not_in_finish_stage() {
            // Given
            testPlayer.setActualStage(mockGameRoom);

            // When
            boolean result = testPlayer.checkIsUserInFinishSage();

            // Then
            assertFalse(result);
        }
    }

    @Nested
    @DisplayName("Get User Input Tests")
    class GetUserInputTests {

        @Test
        @DisplayName("Should read user input from socket")
        void getUserInput_should_read_from_socket() throws IOException {
            // Given
            String expectedInput = "test input";
            InputStream inputStream = new ByteArrayInputStream((expectedInput + "\n").getBytes());
            when(mockSocket.getInputStream()).thenReturn(inputStream);

            testPlayer = new Player(
                    1, "TestUser", 0, 3, false, mockClientDispatch, mockSocket, mockWaitingRoom, false, false);

            // When
            String result = testPlayer.testGetUserInput();

            // Then
            assertEquals(expectedInput, result);
        }

        @Test
        @DisplayName("Should throw RuntimeException when IOException occurs")
        void getUserInput_should_throw_runtime_exception_on_io_error() throws IOException {
            // Given
            when(mockSocket.getInputStream()).thenThrow(new IOException("Test exception"));

            testPlayer = new Player(
                    1, "TestUser", 0, 3, false, mockClientDispatch, mockSocket, mockWaitingRoom, false, false);

            // When & Then
            assertThrows(RuntimeException.class, () -> {
                testPlayer.testGetUserInput();
            });
        }
    }
}
