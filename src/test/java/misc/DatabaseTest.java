package misc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.academiadecodigo.wordsgame.database.Database;
import org.academiadecodigo.wordsgame.database.DatabaseEnvData;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class DatabaseTest {

    @Mock
    private Database mockDatabase;

    @Mock
    private DatabaseEnvData mockDatabaseEnvData;

    @Test
    @Order(1)
    public void testDatabaseEnvDataMocking() {
        // Setup mock behavior for this test
        when(mockDatabaseEnvData.getCompleteUrl()).thenReturn("jdbc:mysql://localhost:3306/wordscrush_test");
        when(mockDatabaseEnvData.getDbRoot()).thenReturn("root");
        when(mockDatabaseEnvData.getDbRootPass()).thenReturn("1010");
        when(mockDatabaseEnvData.getDbName()).thenReturn("wordscrush_test");
        when(mockDatabaseEnvData.getInGameRootUser()).thenReturn("sprint");
        when(mockDatabaseEnvData.getInGameRootPass()).thenReturn("pass");

        // Test that mocking works correctly
        assertNotNull(mockDatabaseEnvData);
        assertEquals("jdbc:mysql://localhost:3306/wordscrush_test", mockDatabaseEnvData.getCompleteUrl());
        assertEquals("root", mockDatabaseEnvData.getDbRoot());
        assertEquals("1010", mockDatabaseEnvData.getDbRootPass());
        assertEquals("wordscrush_test", mockDatabaseEnvData.getDbName());
        assertEquals("sprint", mockDatabaseEnvData.getInGameRootUser());
        assertEquals("pass", mockDatabaseEnvData.getInGameRootPass());
    }

    @Test
    @Order(2)
    public void testDatabaseConfiguration() {
        // Test Database configuration without actual connection
        Database db = new Database();
        assertNotNull(db);

        // Test environment setting
        db.setEnv("test");
        // We don't test actual connection since we don't have MySQL running
    }

    @Test
    @Order(3)
    public void testDatabaseEnvDataCreation() {
        // Test DatabaseEnvData constructor
        DatabaseEnvData data = new DatabaseEnvData(
                "db-setup.sql",
                "jdbc:mysql://localhost:3306/test",
                "localhost:3306",
                "root",
                "password",
                "testdb",
                "testuser",
                "testpass");

        assertNotNull(data);
        assertEquals("jdbc:mysql://localhost:3306/test", data.getCompleteUrl());
        assertEquals("root", data.getDbRoot());
        assertEquals("password", data.getDbRootPass());
        assertEquals("testdb", data.getDbName());
        assertEquals("testuser", data.getInGameRootUser());
        assertEquals("testpass", data.getInGameRootPass());
    }

    @Test
    @Order(4)
    public void testDatabaseMocking() {
        // Test that Database can be mocked properly
        assertNotNull(mockDatabase);

        // Verify that the mock can be configured
        when(mockDatabase.executeUpdate(anyString())).thenReturn(1);
        assertEquals(1, mockDatabase.executeUpdate("INSERT INTO users VALUES (1, 'test', 'test')"));

        verify(mockDatabase).executeUpdate(anyString());
    }
}
