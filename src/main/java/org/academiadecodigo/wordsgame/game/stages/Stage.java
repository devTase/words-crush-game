package org.academiadecodigo.wordsgame.game.stages;

import org.academiadecodigo.wordsgame.entities.users.User;
import org.academiadecodigo.wordsgame.game.commands.CommandRunner;
import org.academiadecodigo.wordsgame.game.commands.executors.*;
import org.academiadecodigo.wordsgame.game.grid.game.Grid;
import java.util.Arrays;
import java.util.List;

public abstract class Stage implements StageInterface  {

    private CommandRunner commandRunner;
    private Grid grid;
    private int maxPlayers;
    private List<User> usersInTheRoom;

    public Stage(Grid grid, int maxPlayers, List<User> usersInTheRoom) {
        this.grid = grid;
        this.maxPlayers = maxPlayers;
        this.usersInTheRoom = usersInTheRoom;
        this.commandRunner = new CommandRunner(Arrays.asList(
                new KickCommandExecutor(),
                new ListCommandExecutor(),
                new PmCommandExecutor(),
                new StartCommandExecutor(),
                new StartAllCommandExecutor(),
                new HelpCommandExecutor()));
    }

    public void playerLost(User user) {}

    // Getters and Setters
    public CommandRunner getCommandRunner() {
        return commandRunner;
    }

    public void setCommandRunner(CommandRunner commandRunner) {
        this.commandRunner = commandRunner;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public List<User> getUsersInTheRoom() {
        return usersInTheRoom;
    }

    public void setUsersInTheRoom(List<User> usersInTheRoom) {
        this.usersInTheRoom = usersInTheRoom;
    }
}
