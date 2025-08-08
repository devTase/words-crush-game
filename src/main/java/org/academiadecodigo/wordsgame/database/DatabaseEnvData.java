package org.academiadecodigo.wordsgame.database;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.academiadecodigo.wordsgame.entities.users.Role;

public class DatabaseEnvData {
    String databaseSetupFilePath;
    String completeUrl;
    String url;
    String dbRoot;
    String dbRootPass;
    String dbName;
    String inGameRootUser;
    String inGameRootPass;
    String rootRole;
    String enumRoles;

    public DatabaseEnvData(
            String databaseSetupFilePath,
            String completeUrl,
            String url,
            String dbRoot,
            String dbRootPass,
            String dbName,
            String inGameRootUser,
            String inGameRootPass) {
        this.databaseSetupFilePath = databaseSetupFilePath;
        this.completeUrl = completeUrl;
        this.url = url;
        this.dbRoot = dbRoot;
        this.dbRootPass = dbRootPass;
        this.dbName = dbName;
        this.inGameRootUser = inGameRootUser;
        this.inGameRootPass = inGameRootPass;
        this.rootRole = Role.ROOT.name();
        this.enumRoles = this.getEnumRolesAsString();
    }

    private String getEnumRolesAsString() {
        return Stream.of(Role.values()).map(role -> "'" + role.name() + "'").collect(Collectors.joining(", "));
    }

    // Getters and Setters
    public String getDatabaseSetupFilePath() {
        return databaseSetupFilePath;
    }

    public void setDatabaseSetupFilePath(String databaseSetupFilePath) {
        this.databaseSetupFilePath = databaseSetupFilePath;
    }

    public String getCompleteUrl() {
        return completeUrl;
    }

    public void setCompleteUrl(String completeUrl) {
        this.completeUrl = completeUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDbRoot() {
        return dbRoot;
    }

    public void setDbRoot(String dbRoot) {
        this.dbRoot = dbRoot;
    }

    public String getDbRootPass() {
        return dbRootPass;
    }

    public void setDbRootPass(String dbRootPass) {
        this.dbRootPass = dbRootPass;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getInGameRootUser() {
        return inGameRootUser;
    }

    public void setInGameRootUser(String inGameRootUser) {
        this.inGameRootUser = inGameRootUser;
    }

    public String getInGameRootPass() {
        return inGameRootPass;
    }

    public void setInGameRootPass(String inGameRootPass) {
        this.inGameRootPass = inGameRootPass;
    }

    public String getRootRole() {
        return rootRole;
    }

    public void setRootRole(String rootRole) {
        this.rootRole = rootRole;
    }

    public String getEnumRoles() {
        return enumRoles;
    }

    public void setEnumRoles(String enumRoles) {
        this.enumRoles = enumRoles;
    }
}
