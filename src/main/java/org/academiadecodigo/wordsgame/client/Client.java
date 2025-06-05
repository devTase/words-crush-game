package org.academiadecodigo.wordsgame.client;

import org.academiadecodigo.wordsgame.client.controllers.GameController;
import org.academiadecodigo.wordsgame.client.views.GameView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private static final Logger logger = LoggerFactory.getLogger(Client.class);
    private BufferedReader in;
    private GameView view;

    public void start(String serverAddress, int serverPort) throws IOException {
        logger.info("Iniciando cliente com servidor {}:{}", serverAddress, serverPort);
        
        // Initialize view
        view = new GameView();
        
        // Establish connection
        Socket socket = new Socket(serverAddress, serverPort);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        
        // Initialize controller
        new GameController(view, out);
        
        // Start receiver thread
        startReceiverThread();
        
        // Show view
        view.setVisible(true);
    }

    private void startReceiverThread() {
        logger.debug("Iniciando thread de recebimento");
        new Thread(() -> {
            try {
                while (true) {
                    String message = in.readLine();
                    if (message == null) {
                        logger.warn("Conexão encerrada pelo servidor");
                        break;
                    }
                    logger.debug("Mensagem recebida: {}", message);
                    view.getChatArea().append(message + "\n");
                }
            } catch (IOException ex) {
                logger.error("Erro na thread de recebimento", ex);
            }
        }).start();
    }

    public static void main(String[] args) throws IOException {
        String serverAddress;
        int serverPort;

        // Get server address and port from user input
        javax.swing.JTextField addressField = new javax.swing.JTextField(10);
        addressField.setText("127.0.0.1");
        javax.swing.JTextField portField = new javax.swing.JTextField(5);
        portField.setText("8001");
        Object[] fields = {"Server Address: ", addressField, "Port: ", portField};
        int option = javax.swing.JOptionPane.showConfirmDialog(null, fields, "Connect to Server", javax.swing.JOptionPane.OK_CANCEL_OPTION);
        if (option == javax.swing.JOptionPane.OK_OPTION) {
            serverAddress = addressField.getText();
            serverPort = Integer.parseInt(portField.getText());
        } else {
            return;
        }

        // Create and show the chat window
        Client game = new Client();
        game.start(serverAddress, serverPort);
    }
}
