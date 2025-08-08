package org.academiadecodigo.wordsgame.application.server;

import org.academiadecodigo.wordsgame.config.ClientExecutorService;
import org.academiadecodigo.wordsgame.config.GameConfiguration;
import org.academiadecodigo.wordsgame.game.ChatCommandsMessagesTrafficManager;
import org.academiadecodigo.wordsgame.database.Database;
import org.academiadecodigo.wordsgame.misc.Messages;

import jakarta.inject.Inject;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class GameServer {

    private final GameConfiguration config;
    private final Database database;
    private final ExecutorService clientExecutor;
    private final ClientDispatchFactory clientDispatchFactory;
    private ExecutorCompletionService<Void> executorCompletionService;
    private ServerSocket serverSocket;

    @Inject
    public GameServer(GameConfiguration config, 
                     Database database,
                     @ClientExecutorService ExecutorService clientExecutor,
                     ClientDispatchFactory clientDispatchFactory) throws IOException {
        this.config = config;
        this.database = database;
        this.clientExecutor = clientExecutor;
        this.clientDispatchFactory = clientDispatchFactory;
        
        this.executorCompletionService = new ExecutorCompletionService<>(clientExecutor);
        this.serverSocket = new ServerSocket(config.getServerPort());

        ChatCommandsMessagesTrafficManager.sendMessageToServer(Messages.getMessage("INFO_SERVER_ON"));
        ChatCommandsMessagesTrafficManager.sendMessageToServer(Messages.getMessage("INFO_PORT") + config.getServerPort());
    }

    public void manageNewConnections() {
        int clientNumber = 0;
        while (clientNumber < config.getMaxClients()) {
            try {
                Socket clientSocket = serverSocket.accept();
                ClientDispatch clientDispatch = clientDispatchFactory.create(clientSocket);
                executorCompletionService.submit(clientDispatch, null);
                clientNumber++;
                ChatCommandsMessagesTrafficManager.sendMessageToServer(Messages.getMessage("INFO_NEWCONNECTION") + clientNumber);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void close() throws IOException {
        System.out.println("Closing server socket...");
        serverSocket.close();
        clientExecutor.shutdownNow();
    }
}
