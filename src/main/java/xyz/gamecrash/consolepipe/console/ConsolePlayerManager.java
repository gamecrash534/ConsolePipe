package xyz.gamecrash.consolepipe.console;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;

import java.util.Objects;

import static xyz.gamecrash.consolepipe.utils.MessageUtils.*;

public class ConsolePlayerManager extends AbstractConsolePlayerCache {

    public Filter.Result filter(LogEvent e) {
        String sender = Objects.equals(e.getLoggerName(), "") ? "Console" : e.getLoggerName();
        String msg = removeAnsiCodes(e.getMessage().getFormattedMessage());
        players.stream()
            .filter(player -> !msg.matches(player.getDenyReg()))
            .filter(player -> !e.getLoggerName().matches(player.getDenyReg()))
            .forEach(player -> player.getPlayer().sendMessage(returnComponent(formatPipedMessage(msg, sender))));
        return Filter.Result.NEUTRAL;
    }
}