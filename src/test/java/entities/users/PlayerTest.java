package entities.users;

import org.academiadecodigo.wordsgame.application.server.ClientDispatch;
import org.academiadecodigo.wordsgame.entities.users.Player;
import org.academiadecodigo.wordsgame.game.stages.WaitingRoom;
import org.academiadecodigo.wordsgame.game.stages.GameRoom;
import org.academiadecodigo.wordsgame.game.stages.FinishRoom;
import org.academiadecodigo.wordsgame.game.stages.Stage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PlayerTest {

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
    
    private Player player;

    @BeforeEach
    public void setUp() {
        player = new Player(1, "testUser", 0, 3, false, mockClientDispatch, mockSocket, mockWaitingRoom, false, false);
    }

    @Nested
    @DisplayName("kick() method tests")
    class KickMethodTests {

        @Test
        @DisplayName("Should set isKicked to true and close socket when kick() is called")
        void kick_should_set_isKicked_true_and_close_socket() throws IOException {
            // given
            assertFalse(player.isKicked());
            
            // when
            player.kick();
            
            // then
            assertTrue(player.isKicked());
            verify(mockSocket).close();
        }

        @Test
        @DisplayName("Should throw RuntimeException when IOException occurs during socket close")
        void kick_should_throw_RuntimeException_when_IOException_occurs() throws IOException {
            // given
            doThrow(new IOException("Socket error")).when(mockSocket).close();
            
            // when & then
            RuntimeException exception = assertThrows(RuntimeException.class, () -> player.kick());
            assertTrue(player.isKicked());
            assertNotNull(exception.getCause());
            assertTrue(exception.getCause() instanceof IOException);
        }
    }

    @Nested
    @DisplayName("Player state and behavior tests")
    class PlayerStateTests {

        @Test
        @DisplayName("Should return correct stage when getActualStage is called")
        void getActualStage_should_return_correct_stage() {
            // given
            player.setActualStage(mockGameRoom);
            
            // when
            Stage result = player.getActualStage();
            
            // then
            assertEquals(mockGameRoom, result);
        }

        @Test
        @DisplayName("Should maintain kicked state correctly")
        void kicked_state_should_be_maintained_correctly() {
            // given
            assertFalse(player.isKicked());
            
            // when
            player.setKicked(true);
            
            // then
            assertTrue(player.isKicked());
            
            // when
            player.setKicked(false);
            
            // then
            assertFalse(player.isKicked());
        }

        @Test
        @DisplayName("Should start with correct initial state")
        void should_start_with_correct_initial_state() {
            // given - player created in setUp
            
            // then
            assertFalse(player.isKicked());
            assertEquals(mockWaitingRoom, player.getActualStage());
            assertEquals("testUser", player.getUserName());
            assertEquals(1, player.getId());
            assertEquals(0, player.getScore());
            assertEquals(3, player.getLives());
        }
    }
}
