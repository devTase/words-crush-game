package org.academiadecodigo.wordsgame.game.stages;

import java.util.List;
import org.academiadecodigo.wordsgame.entities.users.User;

public interface StageInterface extends Runnable {

    void checkUserInput(User user, String message);

    List<User> getUsersInTheRoom();
}
