package org.academiadecodigo.wordsgame.entities.users;

import org.academiadecodigo.wordsgame.prompt.Prompt;
import org.academiadecodigo.wordsgame.game.PromptMenu;
import org.academiadecodigo.wordsgame.misc.Messages;
import org.academiadecodigo.wordsgame.service.UserAuthenticator;

import java.io.PrintWriter;

public class UserManager {

    private final PrintWriter outStream;
    private final PromptMenu<String> promptMenuString;
    private final PromptMenu<Integer> promptMenuInt;
    private final Prompt prompt;
    private final UserAuthenticator userAuthenticator;
    private String userName;

    public UserManager(PrintWriter outStream, Prompt prompt, UserAuthenticator userAuthenticator) {
        this.outStream = outStream;
        this.promptMenuInt = new PromptMenu<>();
        this.promptMenuString = new PromptMenu<>();
        this.prompt = prompt;
        this.userAuthenticator = userAuthenticator;
    }

    public void register() {
        Integer accountType = promptMenuInt.createNewMenu(new String[]{"Admin", "Player"}, "Select an account type to create:", prompt);

        switch (accountType) {
            case 1 -> adminRegistration();
            case 2 -> playerRegistration();
            default -> {
            }
        }
    }

    public void login() {
        outStream.println("Login with your details:");
        String userName = promptMenuString.createNewQuestion(Messages.getMessage("INFO_SET_NICKNAME"), prompt);
        String password = promptMenuString.createNewQuestion(Messages.getMessage("INFO_SET_PASSWORD"), prompt);

        if(userAuthenticator.login(userName, password)) {
            outStream.println(Messages.getMessage("WELCOME"));
            this.userName = userName;
            //TODO: Show a dashboard with all the user data here
        } else {
            outStream.println("No User Found with this details. Bye");
            outStream.close();
            //TODO Should close the client
        }
    }

    public Role getUserRole() {
        return userAuthenticator.getUserRole(userName);
    }

    public String getUserName() {
        return this.userName;
    }

    private void adminRegistration() {
        String userName = promptMenuString.createNewQuestion("Login with root privileges first.\nRootName: ", prompt);
        String password = promptMenuString.createNewQuestion(Messages.getMessage("INFO_SET_PASSWORD"), prompt);

        if(isRoot(userName, password)) {
            userName = promptMenuString.createNewQuestion("Set Admin Name: ", prompt);
            password = promptMenuString.createNewQuestion(Messages.getMessage("INFO_SET_PASSWORD"), prompt);

            userAuthenticator.register(Role.ADMIN, userName, password);
            outStream.println("A new Admin Account was configured");
            return;
        }
        outStream.println("ROOT details wrong. Proceed with a normal account");
        outStream.flush();
        this.playerRegistration();
    }

    private void playerRegistration() {
        String userName = promptMenuString.createNewQuestion(Messages.getMessage("INFO_SET_NICKNAME"), prompt);
        String password = promptMenuString.createNewQuestion(Messages.getMessage("INFO_SET_PASSWORD"), prompt);
        userAuthenticator.register(Role.PLAYER, userName, password);
    }

    private boolean isRoot(String user, String pass) {
        return userAuthenticator.authenticateRoot(user, pass);
    }
}
