package xyz.gamecrash.consolepipe.utils;

import org.bukkit.command.CommandSender;
import xyz.gamecrash.consolepipe.ConsolePipe;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import xyz.gamecrash.consolepipe.config.ConfigEntries;

import java.time.LocalTime;

import static xyz.gamecrash.consolepipe.config.Messages.*;

public class MessageUtils {
    private static final ConsolePipe plugin = ConsolePipe.getPlugin();

    public static String returnPrefix(String message) {
        boolean enabled = plugin.getConfig().getBoolean(ConfigEntries.PREFIX_ENABLED);
        if (enabled) { return ConsolePipe.getPlugin().getConfig().getString(MESSAGE_PREFIX) + message; }
        return message;
    }
    public static Component returnComponent(String message) {
        MiniMessage builder = MiniMessage.builder().build();
        return builder.deserialize(message);
    }
    public static String returnConfig(String path) {
        return ConsolePipe.getPlugin().getConfig().getString(path);
    }
    public static Component message(String message) {
        return returnComponent(returnPrefix(message));
    }

    public static void sendMessage(CommandSender sender, String message) {
        if (sender == null || message == null || message.isEmpty()) {
            return;
        }
        sender.sendMessage(message(message));
    }
    public static void sendConfigMessage(CommandSender sender, String path) {
        String message = returnConfig(path);
        sendMessage(sender, message);
    }

    public static String formatPipedMessage(String message, String sender) {
        return returnConfig(MESSAGE_PIPE_FORMAT).replace("%timestamp%", String.valueOf(LocalTime.now()).replaceAll("\\..*", ""))
            .replace("%sender%", sender)
            .replace("%message%", message);
    }
    public static String removeAnsiCodes(String message) {
        return message.replaceAll("\u001B\\[[;\\d]*m", "");
    }
}
