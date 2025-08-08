package org.academiadecodigo.wordsgame.config;

import jakarta.inject.Inject;
import org.academiadecodigo.wordsgame.entities.users.Role;
import org.academiadecodigo.wordsgame.service.UserAuthenticator;

public class DemoUserAuthenticator extends UserAuthenticator {

    private final DemoDatabase demoDatabase;

    @Inject
    public DemoUserAuthenticator(DemoDatabase demoDatabase) {
        super(demoDatabase); // Call parent constructor
        this.demoDatabase = demoDatabase;
    }

    @Override
    public boolean authenticateRoot(String user, String pass) {
        return demoDatabase.authenticateUser(user, pass, "ROOT");
    }

    @Override
    public void register(Role role, String userName, String password) {
        demoDatabase.addUser(userName, password, role.toString());
    }

    @Override
    public boolean login(String userName, String password) {
        return demoDatabase.loginUser(userName, password);
    }

    @Override
    public Role getUserRole(String userName) {
        String roleStr = demoDatabase.getUserRole(userName);
        return roleStr != null ? Role.valueOf(roleStr.toUpperCase()) : null;
    }
}
