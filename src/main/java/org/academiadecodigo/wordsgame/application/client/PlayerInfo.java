package org.academiadecodigo.wordsgame.application.client;

/**
 * Represents player information for UI display
 */
public class PlayerInfo {
    private String name;
    private boolean isReady;
    private boolean isAdmin;

    public PlayerInfo(String name, boolean isReady, boolean isAdmin) {
        this.name = name;
        this.isReady = isReady;
        this.isAdmin = isAdmin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public String toString() {
        String status = isReady ? "✅ Ready" : "⏳ Not Ready";
        String role = isAdmin ? "👑" : "👤";
        return String.format("%s %s - %s", role, name, status);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PlayerInfo that = (PlayerInfo) obj;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
