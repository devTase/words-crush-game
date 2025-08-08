package org.academiadecodigo.wordsgame.game.stages;

import java.util.List;
import org.academiadecodigo.wordsgame.entities.users.User;
import org.academiadecodigo.wordsgame.game.grid.game.Grid;

public class FinishRoom extends Stage {

    private User winner;

    public FinishRoom(Grid grid, int maxPlayers, List<User> usersInTheRoom) {
        super(grid, maxPlayers, usersInTheRoom);
    }

    @Override
    public void checkUserInput(User user, String message) {}

    @Override
    public void run() {}

    /**
     * Set Winner
     * @param winner
     */
    public void setWinner(User winner) {
        this.winner = winner;
        winner.setActualStage(this);
    }

    /**
     * Add user to this stage
     * @param user
     */
    public void addUserToStage(User user) {
        user.setActualStage(this);
        getUsersInTheRoom().add(user);
    }

    public User getWinner() {
        return winner;
    }
}
