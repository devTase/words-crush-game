package org.academiadecodigo.wordsgame.game.stages;

import org.academiadecodigo.wordsgame.application.server.GameServer;
import org.academiadecodigo.wordsgame.entities.users.User;
import org.academiadecodigo.wordsgame.config.GameConfiguration;
import org.academiadecodigo.wordsgame.game.ChatCommandsMessagesTrafficManager;
import org.academiadecodigo.wordsgame.game.grid.game.Grid;
import org.academiadecodigo.wordsgame.game.grid.server.ServerGrid;
import org.academiadecodigo.wordsgame.misc.Messages;
import java.util.List;

public class GameRoom extends Stage {

    private ServerGrid sg;
    private Stage finishStage;
    private boolean gameIsFinished = false;

    public GameRoom(Grid grid, int maxPlayers, List<User> usersInTheRoom) {
        super(grid, maxPlayers, usersInTheRoom);
        this.sg = new ServerGrid();
        ChatCommandsMessagesTrafficManager.sendMessageToAll(Messages.getMessage("SET_YOURSELF_READY"));
    }

    public void startStage() {
        setupGrid();
    }

    @Override
    public void checkUserInput(User user, String message) {

        int score = getGrid().checkPlayerInput(message);
        if(score > 0) {
            user.setScore(user.getScore()+score);
            return;
        }
        user.setLives(user.getLives()-1);
    }

    /**
     * Setup the grid with the words
     */
    public void setupGrid() {
        getGrid().setWordsForMatrix();
    }

    @Override
    public void run() {

        startStage();
        this.finishStage = ChangeStage.setFinishStage(this);
        ChatCommandsMessagesTrafficManager.clearScreenServerSide();
    }

    public synchronized void playerLost(User user){
        ((FinishRoom) finishStage).addUserToStage(user);
        if(finishStage.getUsersInTheRoom().size() == getMaxPlayers()-1) checkForTheWinner();
    }

    private void checkForTheWinner() {
        ((FinishRoom)finishStage).setWinner(getWinner());
    }

    private User getWinner() {

        for(User u : this.getUsersInTheRoom()) {
            if(!finishStage.getUsersInTheRoom().contains(u)) {
                gameIsFinished = true;
                return u;
            }
        }
        return null;
    }

    // Getters and Setters
    public ServerGrid getSg() {
        return sg;
    }

    public void setSg(ServerGrid sg) {
        this.sg = sg;
    }

    public Stage getFinishStage() {
        return finishStage;
    }

    public void setFinishStage(Stage finishStage) {
        this.finishStage = finishStage;
    }

    public boolean isGameIsFinished() {
        return gameIsFinished;
    }

    public void setGameIsFinished(boolean gameIsFinished) {
        this.gameIsFinished = gameIsFinished;
    }
}
