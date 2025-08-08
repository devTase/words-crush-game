package org.academiadecodigo.wordsgame.entities.users;

import org.academiadecodigo.wordsgame.application.server.ClientDispatch;
import org.academiadecodigo.wordsgame.game.stages.Stage;
import org.academiadecodigo.wordsgame.game.stages.WaitingRoom;
import org.academiadecodigo.wordsgame.game.stages.GameRoom;
import org.academiadecodigo.wordsgame.game.stages.FinishRoom;

import java.net.Socket;
public class Admin extends User implements Runnable {

    public Admin(int id, String userName, int score, int lives, boolean isReady, ClientDispatch clientDispatch, Socket socket, Stage actualStage, Boolean isReadyConfirmed) {
        super(id, userName, score, lives, isReady, clientDispatch, socket, actualStage, isReadyConfirmed);
    }

    @Override
    public void run() {

        while(isUserInWaitingRoom()) {
            behaviourInWaitingRoom();
        }

        try {
            Thread.sleep(1000); //Wait 1 sec so the grid has time to setup.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while(isUserInGameRoom()){
            behaviourInGameRoom();
        }

        if(isUserInFinishSage()) {
            behaviourInFinishGame();
        }
    }
}
