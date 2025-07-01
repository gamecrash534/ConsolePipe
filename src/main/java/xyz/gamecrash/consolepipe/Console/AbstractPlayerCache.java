package xyz.gamecrash.consolepipe.Console;

import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPlayerCache extends AbstractFilter {
    protected final List<ConsolePlayer> players = new ArrayList<>();

    public void clear() {
        players.clear();
    }

    public List<ConsolePlayer> getPlayers() {
        return new ArrayList<>(players);
    }

    public ConsolePlayer getPlayer(Player player) {
        if (player == null) return null;
        return players.stream()
                .filter(p -> p.getUuid().equals(player.getUniqueId()))
                .findFirst()
                .orElse(null);
    }

    public boolean contains(Player player) {
        if (player == null) return false;
        return players.stream().anyMatch(p -> p.getUuid().equals(player.getUniqueId()));
    }

    public void addPlayer(Player player) {
        if (player == null) return;
        ConsolePlayer consolePlayer = new ConsolePlayer(player.getUniqueId(), player.getName());
        if (!players.contains(consolePlayer)) {
            players.add(consolePlayer);
        }
    }
    public void removePlayer(Player player) {
        if (player == null) return;
        players.removeIf(p -> p.getUuid().equals(player.getUniqueId()));
    }
}
