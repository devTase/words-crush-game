package org.academiadecodigo.wordsgame.game.commands.executors;

import java.util.List;
import org.academiadecodigo.wordsgame.entities.users.User;
import org.academiadecodigo.wordsgame.game.ChatCommandsMessagesTrafficManager;
import org.academiadecodigo.wordsgame.game.PlayersUpdateBroadcaster;
import org.academiadecodigo.wordsgame.game.commands.executors.list.CommandsList;

public class ReadyCommandExecutor extends CommandExecutor {

    public static final String READY_COMMAND = CommandsList.READY.getCommand();

    @Override
    public boolean isApplicable(String command) {
        return command.equals(READY_COMMAND);
    }

    @Override
    protected String executeValidCommand(String command, User user, List<User> userList) {
        user.setReady(true);

        // Broadcast updated players list to all users
        PlayersUpdateBroadcaster.broadcastPlayersUpdate(userList);

        return ChatCommandsMessagesTrafficManager.commandStart();
    }
}
