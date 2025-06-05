package org.academiadecodigo.wordsgame.client.controllers;

import org.academiadecodigo.wordsgame.client.views.GameView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

public class GameController implements ActionListener {

    private final GameView view;
    private final PrintWriter out;

    public GameController(GameView view, PrintWriter out) {
        this.view = view;
        this.out = out;
        registerControllers();
    }

    private void registerControllers() {
        view.getMessageField().addActionListener(this);
        view.getSendButton().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String message = view.getMessageField().getText();
        out.println(message);
        view.getMessageField().setText("");
    }
}
