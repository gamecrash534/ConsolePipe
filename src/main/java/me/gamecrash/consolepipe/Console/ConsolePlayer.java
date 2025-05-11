package me.gamecrash.consolepipe.Console;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;


public class ConsolePlayer {
    private UUID uuid;
    private String name;
    public String formatRegex = "";
    public String denyRegex = "";

    public ConsolePlayer(UUID uuid, String name, String formatRegex, String denyRegex) {
        this.uuid = uuid;
        this.name = name;
        this.formatRegex = formatRegex;
        this.denyRegex = denyRegex;
    }
    public UUID getUuid() {
        return uuid;
    }
    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }
}
