package me.gamecrash.consolepipe.Console;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.filter.AbstractFilter;

import java.util.List;
import java.util.Objects;

import static me.gamecrash.consolepipe.Utils.MessageUtils.*;

public class ConsoleFilter extends AbstractFilter {
    private volatile List<ConsolePlayer> players;

    public ConsoleFilter() {
        this.players = List.of();
    }

    public void updatePlayers(List<ConsolePlayer> players) {
        this.players = players;
    }

    @Override
    public Result filter(LogEvent e) {
        String sender = Objects.equals(e.getLoggerName(), "") ? "Console" : e.getLoggerName();
        String msg = removeAnsiCodes(e.getMessage().getFormattedMessage());
        players.stream()
            .filter(player -> !msg.matches(player.denyRegex))
            .forEach(player -> player.getPlayer().sendMessage(message(formatPipedMessage(msg, sender))));
        return Result.NEUTRAL;
    }
}