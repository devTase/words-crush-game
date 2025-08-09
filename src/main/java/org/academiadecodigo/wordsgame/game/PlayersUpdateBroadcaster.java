package org.academiadecodigo.wordsgame.game;

import java.util.List;
import org.academiadecodigo.wordsgame.entities.users.Admin;
import org.academiadecodigo.wordsgame.entities.users.User;

/**
 * Handles broadcasting player list updates to all connected clients
 */
public class PlayersUpdateBroadcaster {

    private static final String PLAYERS_UPDATE_PREFIX = "[PLAYERS_UPDATE]:";

    /**
     * Broadcasts the current players list to all connected users
     * @param players List of users currently in the room
     */
    public static void broadcastPlayersUpdate(List<User> players) {
        if (players == null || players.isEmpty()) {
            return;
        }

        String playersData = buildPlayersData(players);
        String message = PLAYERS_UPDATE_PREFIX + " " + playersData;

        // Send to all players
        for (User user : players) {
            if (user.getClientDispatch() != null) {
                try {
                    user.getClientDispatch().notifyPlayer(message);
                } catch (Exception e) {
                    // Log error but continue with other players
                    System.err.println("Error sending players update to " + user.getUserName() + ": " + e.getMessage());
                }
            }
        }
    }

    /**
     * Builds the players data string in the format:
     * "playerName1:ready:admin|playerName2:notready:player|..."
     */
    private static String buildPlayersData(List<User> players) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < players.size(); i++) {
            User user = players.get(i);

            sb.append(user.getUserName())
                    .append(":")
                    .append(user.isReady() ? "ready" : "notready")
                    .append(":")
                    .append(user instanceof Admin ? "admin" : "player");

            // Add separator if not the last player
            if (i < players.size() - 1) {
                sb.append("|");
            }
        }

        return sb.toString();
    }
}
