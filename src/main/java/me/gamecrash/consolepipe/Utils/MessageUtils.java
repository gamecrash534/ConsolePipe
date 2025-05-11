package me.gamecrash.consolepipe.Utils;

import me.gamecrash.consolepipe.ConsolePipe;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import static me.gamecrash.consolepipe.Utils.Messages.*;

public class MessageUtils {
    public static final ConsolePipe plugin = ConsolePipe.getPlugin();
    public static String returnPrefix(String message) {
        boolean enabled = plugin.getConfig().getBoolean("prefixEnabled");
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
}
