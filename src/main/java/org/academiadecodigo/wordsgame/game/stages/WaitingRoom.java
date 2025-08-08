package org.academiadecodigo.wordsgame.game.stages;

import java.util.List;
import org.academiadecodigo.wordsgame.entities.users.User;
import org.academiadecodigo.wordsgame.game.ChatCommandsMessagesTrafficManager;
import org.academiadecodigo.wordsgame.game.grid.game.Grid;

public class WaitingRoom extends Stage {

    private static WaitingRoom waitingRoom;
    private static boolean isRunning = false;

    public WaitingRoom(Grid grid, int maxPlayers, List<User> usersInTheRoom) {
        super(grid, maxPlayers, usersInTheRoom);
    }

    public static WaitingRoom getInstance(Grid grid, int maxPlayers, List<User> usersInTheRoom) {
        if (waitingRoom == null) {
            synchronized (WaitingRoom.class) {
                if (waitingRoom == null) {
                    waitingRoom = new WaitingRoom(grid, maxPlayers, usersInTheRoom);
                }
            }
        }
        return waitingRoom;
    }

    /**
     * Check if all players are ready
     * @return boolean
     */
    private boolean arePlayersReady() {
        return getUsersInTheRoom().size() > 0 && getUsersInTheRoom().stream().allMatch(User::isReady);
    }

    /**
     * Check user Input
     * @param user
     * @param message
     */
    @Override
    public void checkUserInput(User user, String message) {
        if (message.startsWith("/")) {
            user.getClientDispatch().notifyPlayer(getCommandRunner().runCommand(message, user, getUsersInTheRoom()));
            return;
        }
        synchronized (this) {
            ChatCommandsMessagesTrafficManager.sendMessageToChat(user, message);
        }
    }

    /**
     * Add new User to this stage
     * @param user
     */
    public void registerUserInStage(User user) {
        this.getUsersInTheRoom().add(user);
    }

    /**
     * Check if the waiting room thread is already running
     * @return boolean
     */
    public static boolean isRunning() {
        return isRunning;
    }

    /**
     * Force start the waiting room thread
     */
    public static void forceStart() {
        if (waitingRoom != null && !isRunning) {
            synchronized (WaitingRoom.class) {
                if (!isRunning) {
                    isRunning = true;
                    Thread waitingRoomThread = new Thread(waitingRoom);
                    waitingRoomThread.start();
                }
            }
        }
    }

    @Override
    public void run() {
        isRunning = true;

        while (!arePlayersReady()) {}

        Stage nextStage = ChangeStage.changeToGameRoomStage(this);
        Thread t = new Thread(nextStage);
        t.start();

        isRunning = false;
    }
}
