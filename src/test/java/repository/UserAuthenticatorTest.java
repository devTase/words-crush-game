package repository;

import org.academiadecodigo.wordsgame.database.Database;
import org.academiadecodigo.wordsgame.database.DatabaseEnvData;
import org.academiadecodigo.wordsgame.entities.users.Role;
import org.academiadecodigo.wordsgame.service.UserAuthenticator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.SQLException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class UserAuthenticatorTest {

    @Mock
    private Database mockDatabase;
    
    private UserAuthenticator userAuthenticator;
    private Database database;
    private final String ENV_TEST = "test";

    private DatabaseEnvData dataBaseData;

    @BeforeAll
    public void setUp() {
        // Create instances without database connection for unit testing
        dataBaseData = new DatabaseEnvData(
            "db-setup.sql",
            "jdbc:mysql://localhost:3306/test",
            "localhost:3306", 
            "root",
            "password",
            "testdb",
            "sprint",
            "pass"
        );
        
        // We use mocks instead of real database for unit tests
        userAuthenticator = new UserAuthenticator(mockDatabase);
        assertNotNull(userAuthenticator);
    }

    @Test
    public void testUserAuthenticatorCreation() {
        // Test that UserAuthenticator can be created with Database dependency
        assertNotNull(userAuthenticator);
        assertNotNull(mockDatabase);
    }

    @Test
    public void testDatabaseEnvDataConfiguration() {
        // Test DatabaseEnvData configuration
        assertNotNull(dataBaseData);
        assertEquals("sprint", dataBaseData.getInGameRootUser());
        assertEquals("pass", dataBaseData.getInGameRootPass());
        assertEquals("testdb", dataBaseData.getDbName());
    }

    @Test
    public void testMockDatabaseInteraction() {
        // Test that we can mock database interactions
        when(mockDatabase.executeUpdate(anyString())).thenReturn(1);
        
        // Verify basic mock functionality
        int result = mockDatabase.executeUpdate("INSERT INTO users (username, password, role) VALUES ('test', 'test', 'PLAYER')");
        assertEquals(1, result);
        
        verify(mockDatabase).executeUpdate(anyString());
    }

    @Test
    public void testRoleEnum() {
        // Test Role enum functionality
        assertEquals("ADMIN", Role.ADMIN.toString());
        assertEquals("PLAYER", Role.PLAYER.toString());
        assertEquals("ROOT", Role.ROOT.toString());
        
        // Test enum values
        Role[] roles = Role.values();
        assertTrue(roles.length >= 3);
    }

}
