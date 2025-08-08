package entities.users;

import static org.junit.jupiter.api.Assertions.*;

import java.net.Socket;
import org.academiadecodigo.wordsgame.application.server.ClientDispatch;
import org.academiadecodigo.wordsgame.entities.users.Admin;
import org.academiadecodigo.wordsgame.game.stages.FinishRoom;
import org.academiadecodigo.wordsgame.game.stages.GameRoom;
import org.academiadecodigo.wordsgame.game.stages.Stage;
import org.academiadecodigo.wordsgame.game.stages.WaitingRoom;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AdminTest {

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

    private Admin admin;

    @BeforeEach
    public void setUp() {
        admin = new Admin(1, "adminUser", 0, 3, false, mockClientDispatch, mockSocket, mockWaitingRoom, false);
    }

    @Nested
    @DisplayName("Admin state and behavior tests")
    class AdminStateTests {

        @Test
        @DisplayName("Should return correct stage when getActualStage is called")
        void getActualStage_should_return_correct_stage() {
            // given
            admin.setActualStage(mockGameRoom);

            // when
            Stage result = admin.getActualStage();

            // then
            assertEquals(mockGameRoom, result);
        }

        @Test
        @DisplayName("Should implement Runnable interface")
        void should_implement_runnable_interface() {
            // then
            assertTrue(admin instanceof Runnable);
        }

        @Test
        @DisplayName("Should start with correct initial state")
        void should_start_with_correct_initial_state() {
            // given - admin created in setUp

            // then
            assertEquals(mockWaitingRoom, admin.getActualStage());
            assertEquals("adminUser", admin.getUserName());
            assertEquals(1, admin.getId());
            assertEquals(0, admin.getScore());
            assertEquals(3, admin.getLives());
            assertFalse(admin.isReady());
            assertFalse(admin.isReadRules());
        }

        @Test
        @DisplayName("Should allow updating admin state")
        void should_allow_updating_admin_state() {
            // given
            admin.setScore(100);
            admin.setLives(5);
            admin.setReady(true);
            admin.setReadRules(true);

            // then
            assertEquals(100, admin.getScore());
            assertEquals(5, admin.getLives());
            assertTrue(admin.isReady());
            assertTrue(admin.isReadRules());
        }
    }
}
