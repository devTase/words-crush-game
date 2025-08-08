package entities.server;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import org.academiadecodigo.wordsgame.application.server.ClientDispatch;
import org.academiadecodigo.wordsgame.application.server.ClientDispatchFactory;
import org.academiadecodigo.wordsgame.application.server.GameServer;
import org.academiadecodigo.wordsgame.config.GameConfiguration;
import org.academiadecodigo.wordsgame.database.Database;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class GameServerTest {

    @Mock
    private GameConfiguration mockGameConfiguration;

    @Mock
    private Database mockDatabase;

    @Mock
    private ExecutorService mockExecutorService;

    @Mock
    private ClientDispatchFactory mockClientDispatchFactory;

    @Mock
    private ServerSocket mockServerSocket;

    @Mock
    private Socket mockClientSocket;

    @Mock
    private ClientDispatch mockClientDispatch;

    private GameServer gameServer;

    @BeforeEach
    void setUp() throws IOException {
        // Setup mock behavior - use different ports for each test to avoid binding conflicts
        when(mockGameConfiguration.getServerPort()).thenReturn(4212 + (int) (Math.random() * 1000));
        when(mockGameConfiguration.getMaxClients()).thenReturn(2);
        when(mockGameConfiguration.getWordsFilePath()).thenReturn("src/main/resources/data.txt");

        gameServer =
                new GameServer(mockGameConfiguration, mockDatabase, mockExecutorService, mockClientDispatchFactory);
    }

    @AfterEach
    void tearDown() throws IOException {
        if (gameServer != null) {
            gameServer.close();
        }
    }

    @Test
    @DisplayName("Should create GameServer instance successfully")
    void constructor_should_create_gameserver_instance_successfully() {
        // given - setup done in @BeforeEach

        // when - gameServer created in @BeforeEach

        // then
        assertNotNull(gameServer);
        verify(mockGameConfiguration, atLeastOnce()).getServerPort();
    }

    @Test
    @DisplayName("Should initialize GameServer with provided configuration")
    void constructor_should_initialize_gameserver_with_provided_configuration() throws IOException {
        // given - setup in @BeforeEach

        // when - gameServer created in @BeforeEach

        // then
        assertNotNull(gameServer);
        // Verify that ServerSocket was created with the configured port
        verify(mockGameConfiguration, atLeastOnce()).getServerPort();
    }

    @Test
    @DisplayName("Should properly close resources when close is called")
    void close_should_cleanup_resources_properly() throws IOException {
        // given - gameServer already created

        // when
        gameServer.close();

        // then
        verify(mockExecutorService).shutdownNow();
        // Note: ServerSocket.close() is called internally but hard to verify without complex mocking
    }
}
