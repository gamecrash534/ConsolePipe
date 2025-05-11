package me.gamecrash.consolepipe.Console;

import me.gamecrash.consolepipe.ConsolePipe;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.filter.AbstractFilter;

import java.time.LocalTime;
import java.util.List;

import static me.gamecrash.consolepipe.Utils.MessageUtils.message;
import static me.gamecrash.consolepipe.Utils.MessageUtils.returnConfig;
import static me.gamecrash.consolepipe.Utils.Messages.*;

public class ConsoleFilter extends AbstractFilter {
    private final ConsolePipe plugin = ConsolePipe.getPlugin();

    private List<ConsolePlayer> players;

    public ConsoleFilter(List<ConsolePlayer> players) {
        this.players = players;
    }

    @Override
    public Result filter(LogEvent e) {
        String sender = e.getLoggerName() == null ? "Console" : e.getLoggerName();
        String msg = e.getMessage().getFormattedMessage();
        msg = removeAnsiCodes(msg);
        for (ConsolePlayer player : players) {
            if (msg.matches(player.denyRegex)) {
            } else {

                player.getPlayer().sendMessage(message(formatPipedMessage(msg, sender)));
            }
        }
        return Result.NEUTRAL;
    }

    private String formatPipedMessage(String message, String sender) {
        return returnConfig(MESSAGE_PIPE_FORMAT).replace("%timestamp%", String.valueOf(LocalTime.now()).replaceAll("\\..*", ""))
            .replace("%sender%", sender)
            .replace("%message%", message);
    }
    public void setPlayers(List<ConsolePlayer> players) {
        this.players = players;
    }
    private String removeAnsiCodes(String message) {
        return message.replaceAll("\u001B\\[[;\\d]*m", "");
    }
}
