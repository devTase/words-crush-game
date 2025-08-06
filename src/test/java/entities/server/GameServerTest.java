package entities.server;

import org.academiadecodigo.wordsgame.application.server.GameServer;
import org.academiadecodigo.wordsgame.application.server.ClientDispatchFactory;
import org.academiadecodigo.wordsgame.config.GameConfiguration;
import org.academiadecodigo.wordsgame.database.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServerTest {
    
    @Mock
    private GameConfiguration mockGameConfiguration;
    
    @Mock
    private Database mockDatabase;
    
    @Mock
    private ExecutorService mockExecutorService;
    
    @Mock
    private ClientDispatchFactory mockClientDispatchFactory;
    
    private GameServer gameServer;

    @BeforeEach
    void setUp() throws IOException {
        // Setup mock behavior
        when(mockGameConfiguration.getServerPort()).thenReturn(4212);
        when(mockGameConfiguration.getMaxClients()).thenReturn(2);
        when(mockGameConfiguration.getWordsFilePath()).thenReturn("src/main/resources/data.txt");
        
        gameServer = new GameServer(mockGameConfiguration, mockDatabase, mockExecutorService, mockClientDispatchFactory);
    }

    @Test
    void testGameServerCreation() {
        assertNotNull(gameServer);
        // Verify that the mocks were used correctly
        verify(mockGameConfiguration).getServerPort();
    }

    @Test
    void testGameServerBasicFunctionality() {
        // Test basic functionality without needing actual network resources
        assertNotNull(gameServer);
        
        // Verify mock interactions
        verify(mockGameConfiguration).getServerPort();
    }
}
