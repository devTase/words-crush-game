package game;

import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.academiadecodigo.wordsgame.application.server.ClientDispatch;
import org.academiadecodigo.wordsgame.entities.users.Admin;
import org.academiadecodigo.wordsgame.entities.users.Player;
import org.academiadecodigo.wordsgame.entities.users.User;
import org.academiadecodigo.wordsgame.game.PlayersUpdateBroadcaster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PlayersUpdateBroadcasterTest {

    @Mock
    private Player mockPlayer1;

    @Mock
    private Player mockPlayer2;

    @Mock
    private Admin mockAdmin;

    @Mock
    private ClientDispatch mockClientDispatch1;

    @Mock
    private ClientDispatch mockClientDispatch2;

    @Mock
    private ClientDispatch mockClientDispatch3;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(mockPlayer1.getClientDispatch()).thenReturn(mockClientDispatch1);
        when(mockPlayer1.getUserName()).thenReturn("Player1");
        when(mockPlayer1.isReady()).thenReturn(false);

        when(mockPlayer2.getClientDispatch()).thenReturn(mockClientDispatch2);
        when(mockPlayer2.getUserName()).thenReturn("Player2");
        when(mockPlayer2.isReady()).thenReturn(true);

        when(mockAdmin.getClientDispatch()).thenReturn(mockClientDispatch3);
        when(mockAdmin.getUserName()).thenReturn("Admin1");
        when(mockAdmin.isReady()).thenReturn(false);
    }

    @Test
    @DisplayName("Should broadcast players update to all users")
    void broadcastPlayersUpdate_should_notify_all_players() {
        // Given
        List<User> players = new ArrayList<>();
        players.add(mockPlayer1);
        players.add(mockPlayer2);

        // When
        PlayersUpdateBroadcaster.broadcastPlayersUpdate(players);

        // Then
        verify(mockClientDispatch1).notifyPlayer(contains("[PLAYERS_UPDATE]:"));
        verify(mockClientDispatch2).notifyPlayer(contains("[PLAYERS_UPDATE]:"));
    }

    @Test
    @DisplayName("Should include player names in broadcast message")
    void broadcastPlayersUpdate_should_include_player_names() {
        // Given
        List<User> players = new ArrayList<>();
        players.add(mockPlayer1);
        players.add(mockPlayer2);

        // When
        PlayersUpdateBroadcaster.broadcastPlayersUpdate(players);

        // Then
        verify(mockClientDispatch1).notifyPlayer(contains("Player1"));
        verify(mockClientDispatch1).notifyPlayer(contains("Player2"));
    }

    @Test
    @DisplayName("Should include ready status in broadcast message")
    void broadcastPlayersUpdate_should_include_ready_status() {
        // Given
        List<User> players = new ArrayList<>();
        players.add(mockPlayer1);
        players.add(mockPlayer2);

        // When
        PlayersUpdateBroadcaster.broadcastPlayersUpdate(players);

        // Then
        verify(mockClientDispatch1).notifyPlayer(contains("notready"));
        verify(mockClientDispatch1).notifyPlayer(contains("ready"));
    }

    @Test
    @DisplayName("Should differentiate between admin and player in message")
    void broadcastPlayersUpdate_should_differentiate_admin_and_player() {
        // Given
        List<User> players = new ArrayList<>();
        players.add(mockPlayer1);
        players.add(mockAdmin);

        // When
        PlayersUpdateBroadcaster.broadcastPlayersUpdate(players);

        // Then
        verify(mockClientDispatch1).notifyPlayer(contains("player"));
        verify(mockClientDispatch1).notifyPlayer(contains("admin"));
    }

    @Test
    @DisplayName("Should not crash when players list is null")
    void broadcastPlayersUpdate_should_handle_null_list() {
        // Given
        List<User> nullList = null;

        // When & Then (should not throw exception)
        PlayersUpdateBroadcaster.broadcastPlayersUpdate(nullList);
    }

    @Test
    @DisplayName("Should not crash when players list is empty")
    void broadcastPlayersUpdate_should_handle_empty_list() {
        // Given
        List<User> emptyList = new ArrayList<>();

        // When & Then (should not throw exception)
        PlayersUpdateBroadcaster.broadcastPlayersUpdate(emptyList);
    }

    @Test
    @DisplayName("Should handle null ClientDispatch gracefully")
    void broadcastPlayersUpdate_should_handle_null_client_dispatch() {
        // Given
        List<User> players = new ArrayList<>();
        when(mockPlayer1.getClientDispatch()).thenReturn(null);
        players.add(mockPlayer1);

        // When & Then (should not throw exception)
        PlayersUpdateBroadcaster.broadcastPlayersUpdate(players);
    }

    @Test
    @DisplayName("Should continue broadcasting even if one client throws exception")
    void broadcastPlayersUpdate_should_continue_on_exception() {
        // Given
        List<User> players = new ArrayList<>();
        players.add(mockPlayer1);
        players.add(mockPlayer2);

        doThrow(new RuntimeException("Connection error"))
                .when(mockClientDispatch1)
                .notifyPlayer(anyString());

        // Capture System.err to verify error logging
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        PrintStream originalErr = System.err;
        System.setErr(new PrintStream(errContent));

        // When
        PlayersUpdateBroadcaster.broadcastPlayersUpdate(players);

        // Then
        verify(mockClientDispatch2).notifyPlayer(contains("[PLAYERS_UPDATE]:"));

        // Restore System.err
        System.setErr(originalErr);
    }

    @Test
    @DisplayName("Should format message correctly with separator")
    void broadcastPlayersUpdate_should_format_with_separator() {
        // Given
        List<User> players = new ArrayList<>();
        players.add(mockPlayer1);
        players.add(mockPlayer2);

        // When
        PlayersUpdateBroadcaster.broadcastPlayersUpdate(players);

        // Then
        verify(mockClientDispatch1).notifyPlayer(contains("|"));
    }
}
