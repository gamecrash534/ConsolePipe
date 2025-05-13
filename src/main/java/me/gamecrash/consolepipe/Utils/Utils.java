package me.gamecrash.consolepipe.Utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Utils {
    public static boolean checkPlayer(CommandSender sender) {
        return sender instanceof Player;
    }
}
