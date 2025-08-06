package org.academiadecodigo.wordsgame.application.server;

import com.google.inject.Injector;
import org.academiadecodigo.wordsgame.config.GameConfiguration;
import org.academiadecodigo.wordsgame.service.UserAuthenticator;

import javax.inject.Inject;
import java.net.Socket;

public class ClientDispatchFactory {

    private final Injector injector;
    private final GameConfiguration config;
    private final UserAuthenticator userAuthenticator;

    @Inject
    public ClientDispatchFactory(Injector injector, 
                               GameConfiguration config,
                               UserAuthenticator userAuthenticator) {
        this.injector = injector;
        this.config = config;
        this.userAuthenticator = userAuthenticator;
    }

    public ClientDispatch create(Socket socket) {
        return new ClientDispatch(socket, config.getWordsFilePath(), userAuthenticator, config);
    }
}
