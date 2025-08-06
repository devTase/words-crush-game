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
    public void setUp() throws SQLException {
        database = new Database(); // Use constructor instead of singleton
        database.setEnv(ENV_TEST);
        database.startDb();
        assertNotNull(database);

        dataBaseData = database.getDataBaseData();
        userAuthenticator = new UserAuthenticator(database); // Create instance with DB
    }

    @Test
    public void testAuthenticateRoot() {
        boolean result = userAuthenticator.authenticateRoot(dataBaseData.getInGameRootUser(), dataBaseData.getInGameRootPass());
        assertTrue(result);
    }

    @Test
    public void testRegister() {
        // Test the register() method
        String mockAdmin = "mock_Admin";
        userAuthenticator.register(Role.ADMIN, mockAdmin, "mock_password");
        assertEquals("ADMIN", Objects.requireNonNull(userAuthenticator.getUserRole(mockAdmin)).toString());
    }

    @Test
    public void testRegisterAdmin() {
        // Authenticate as ROOT user
        boolean authenticated = userAuthenticator.authenticateRoot(dataBaseData.getInGameRootUser(), dataBaseData.getInGameRootPass());
        assertTrue(authenticated, "Failed to authenticate as ROOT user");

        // If authenticated, register new admin user
        if (authenticated) {
            userAuthenticator.register(Role.ADMIN, "newadmin", "newadminpassword");

            // Confirm that the new admin user can log in
            boolean loggedIn = userAuthenticator.login("newadmin", "newadminpassword");
            assertTrue(loggedIn, "Failed to log in as new admin user");

            // Confirm that the new admin user has the ADMIN role
            Role role = userAuthenticator.getUserRole("newadmin");
            assertEquals(Role.ADMIN, role, "New admin user does not have the ADMIN role");
        }
    }

    @Test
    public void testLogin() {
        // Test the login() method
        assertTrue(userAuthenticator.login("sprint", "pass"));
        assertFalse(userAuthenticator.login("admin", "wrongpassword"));
    }

    @Test
    public void testGetUserRole() {
        // Test the getUserRole() method
        assertEquals(Role.ROOT, userAuthenticator.getUserRole(dataBaseData.getInGameRootUser()));
        assertNull(userAuthenticator.getUserRole("nonexistentuser"));
    }

}
