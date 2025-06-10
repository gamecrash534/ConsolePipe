package me.gamecrash.consolepipe.Console;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;


public class ConsolePlayer {
    private UUID uuid;
    private String name;
    private String formatRegex = "";
    private String denyRegex = "";

    public ConsolePlayer(UUID uuid, String name, String formatRegex, String denyRegex) {
        this.uuid = uuid;
        this.name = name;
        this.formatRegex = formatRegex;
        this.denyRegex = denyRegex;
    }
    public ConsolePlayer(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }
    public UUID getUuid() {
        return uuid;
    }
    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }
    public String getFormatReg() { return formatRegex; }
    public String getDenyReg() { return denyRegex; }

    public void setFormatReg(String formatRegex) { this.formatRegex = formatRegex; }
    public void setDenyReg(String denyRegex) { this.denyRegex = denyRegex; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ConsolePlayer that = (ConsolePlayer) obj;
        return uuid.equals(that.uuid) && name.equals(that.name);
    }
    @Override
    public int hashCode() {
        return Objects.hash(uuid, name);
    }
}
