package org.academiadecodigo.wordsgame.entities.users;

import java.io.IOException;
import java.net.Socket;
import org.academiadecodigo.wordsgame.application.server.ClientDispatch;
import org.academiadecodigo.wordsgame.game.stages.FinishRoom;
import org.academiadecodigo.wordsgame.game.stages.GameRoom;
import org.academiadecodigo.wordsgame.game.stages.Stage;
import org.academiadecodigo.wordsgame.game.stages.WaitingRoom;

public class Player extends User {

    private boolean isKicked;

    public Player(
            int id,
            String userName,
            int score,
            int lives,
            boolean isReady,
            ClientDispatch clientDispatch,
            Socket socket,
            Stage actualStage,
            boolean isKicked,
            boolean isReadyConfirmed) {
        super(id, userName, score, lives, isReady, clientDispatch, socket, actualStage, isReadyConfirmed);
        this.isKicked = isKicked;
    }

    @Override
    public void run() {

        while (isUserInWaitingRoom()) {
            behaviourInWaitingRoom();
        }

        try {
            Thread.sleep(1000); // Wait 1 sec so the grid has time to setup.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (isUserInGameRoom()) {
            behaviourInGameRoom();
        }

        if (isUserInFinishSage()) {
            behaviourInFinishGame();
        }
    }

    /**
     * Check if player in Gaming Room
     * @Override Check if Player isKicked.
     * @return boolean
     */
    @Override
    protected boolean isUserInWaitingRoom() {
        return !isKicked && getActualStage() instanceof WaitingRoom;
    }

    /**
     * Check if Player is in playing conditions
     * @Override Check if Player isKicked.
     * @return boolean
     */
    @Override
    protected boolean isUserInGameRoom() {
        return !isKicked && getActualStage() instanceof GameRoom;
    }

    /**
     * Check if Player is in Finish Stage.
     * @Override Check if Player isKicked.
     * @return boolean
     */
    @Override
    protected boolean isUserInFinishSage() {
        return !isKicked && getActualStage() instanceof FinishRoom;
    }

    /**
     * Kicks this player by closing the sockets
     */
    public void kick() {
        isKicked = true;
        try {
            this.getSocket().close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Getter and Setter
    public boolean isKicked() {
        return isKicked;
    }

    public void setKicked(boolean kicked) {
        this.isKicked = kicked;
    }
}
