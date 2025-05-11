package me.gamecrash.consolepipe.Console;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;

import java.util.ArrayList;
import java.util.List;

import static me.gamecrash.consolepipe.Utils.MessageUtils.*;

public class ConsolePlayerManager extends AbstractPlayerCache {
    protected final List<ConsolePlayer> players = new ArrayList<>();

    public Filter.Result filter(LogEvent e) {
        String sender = e.getLoggerName() == null ? "Console" : e.getLoggerName();
        String msg = removeAnsiCodes(e.getMessage().getFormattedMessage());
        players.stream()
            .filter(player -> !msg.matches(player.denyRegex))
            .forEach(player -> player.getPlayer().sendMessage(message(formatPipedMessage(msg, sender))));
        return Filter.Result.NEUTRAL;
    }
}