package me.gamecrash.consolepipe.Listeners;

import me.gamecrash.consolepipe.Console.ConsolePlayerManager;
import me.gamecrash.consolepipe.ConsolePipe;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.logging.Level;

public class PlayerLeaveHandler implements Listener {
    ConsolePipe plugin = ConsolePipe.getPlugin();

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        ConsolePlayerManager cache = plugin.getManager();
        if (cache.contains(e.getPlayer())) { cache.removePlayer(e.getPlayer()); }
        plugin.getLogger().log(Level.INFO, "Player " + e.getPlayer().getName() + " left the console pipe");
    }
}
