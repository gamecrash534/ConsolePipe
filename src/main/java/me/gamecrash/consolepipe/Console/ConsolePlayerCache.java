package me.gamecrash.consolepipe.Console;

import me.gamecrash.consolepipe.ConsolePipe;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ConsolePlayerCache {
    private static ConsolePlayerCache instance;
    private ArrayList<ConsolePlayer> players;
    private ConsolePipe plugin = ConsolePipe.getPlugin();

    public ConsolePlayerCache() {
        instance = this;
        players = new ArrayList<ConsolePlayer>();
    }

    public static ConsolePlayerCache getInstance() {
        return instance;
    }

    public ArrayList<ConsolePlayer> getPlayers() { return players; }
    public void setPlayers(ArrayList<ConsolePlayer> players) {
        this.players = players;
        plugin.setFilterPlayers(players);
    }
    public boolean containsPlayer(ConsolePlayer player) { return (players.contains(player)); }

    public void addPlayer(Player player, String formatReg, String denyReg) {
        if (player == null) return;
        ConsolePlayer consolePlayer = new ConsolePlayer(player.getUniqueId(), player.getName(), formatReg, denyReg);
        if (!containsPlayer(consolePlayer)) {
            players.add(consolePlayer);
        }
        plugin.setFilterPlayers(players);
    }
    public void removePlayer(Player player, String formatReg, String denyReg) {
        if (player == null) return;
        ConsolePlayer consolePlayer = new ConsolePlayer(player.getUniqueId(), player.getName(), formatReg, denyReg);
        if (containsPlayer(consolePlayer)) {
            players.remove(consolePlayer);
        }
        plugin.setFilterPlayers(players);
    }
    public void clear() { players.clear(); }

}
