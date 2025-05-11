package me.gamecrash.consolepipe.Console;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ConsolePlayerCache {
    private static ConsolePlayerCache instance;
    private final List<ConsolePlayer> players = new ArrayList<>();
    private Consumer<List<ConsolePlayer>> onUpdateCallback;

    public ConsolePlayerCache() {
        instance = this;
    }

    public static ConsolePlayerCache getInstance() {
        return instance;
    }

    public List<ConsolePlayer> getPlayers() {
        return new ArrayList<>(players);
    }

    public void setUpdateCallback(Consumer<List<ConsolePlayer>> callback) {
        this.onUpdateCallback = callback;
    }
    public void addPlayer(Player player, String formatReg, String denyReg) {
        if (player == null) return;
        ConsolePlayer consolePlayer = new ConsolePlayer(player.getUniqueId(), player.getName(), formatReg, denyReg);
        if (players.stream().noneMatch(p -> p.equals(consolePlayer))) {
            players.add(consolePlayer);
            notifyUpdate();
        }
    }
    public void removePlayer(Player player) {
        if (player == null) return;
        if (players.removeIf(p -> p.getUuid().equals(player.getUniqueId()))) {
            notifyUpdate();
        }
    }
    public void clear() {
        if (!players.isEmpty()) {
            players.clear();
            notifyUpdate();
        }
    }
    public boolean contains(Player player) {
        if (player == null) return false;
        return players.stream().anyMatch(p -> p.getUuid().equals(player.getUniqueId()));
    }
    private void notifyUpdate() {
        if (onUpdateCallback != null) {
            onUpdateCallback.accept(new ArrayList<>(players));
        }
    }
}