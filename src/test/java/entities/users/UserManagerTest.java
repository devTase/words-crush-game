package entities.users;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import org.academiadecodigo.wordsgame.entities.users.Role;
import org.academiadecodigo.wordsgame.entities.users.UserManager;
import org.academiadecodigo.wordsgame.prompt.Prompt;
import org.academiadecodigo.wordsgame.service.UserAuthenticator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserManagerTest {

    @Mock
    private UserAuthenticator mockUserAuthenticator;

    @Mock
    private Prompt mockPrompt;

    private ByteArrayOutputStream out;
    private PrintWriter printWriter;
    private UserManager userManager;

    @BeforeEach
    public void setUp() {
        out = new ByteArrayOutputStream();
        printWriter = new PrintWriter(out, true);
        userManager = new UserManager(printWriter, mockPrompt, mockUserAuthenticator);
    }

    @Test
    @DisplayName("Should delegate getUserRole to UserAuthenticator with current userName")
    void getUserRole_should_delegate_to_userAuthenticator_with_current_userName() {
        // given
        when(mockUserAuthenticator.getUserRole(null)).thenReturn(null);

        // when
        Role result = userManager.getUserRole();

        // then
        assertNull(result);
        verify(mockUserAuthenticator).getUserRole(null);
    }

    @Test
    @DisplayName("Should return null when no user is logged in")
    void getUserName_should_return_null_when_no_user_logged_in() {
        // given - no setup needed, userManager starts with null userName

        // when
        String result = userManager.getUserName();

        // then
        assertNull(result);
    }

    @Test
    @DisplayName("Should use injected dependencies correctly")
    void userManager_should_use_injected_dependencies_correctly() {
        // given
        assertNotNull(userManager);

        // when - verify that the userManager is created with dependencies
        userManager.getUserRole();

        // then
        verify(mockUserAuthenticator).getUserRole(null);
    }
}
