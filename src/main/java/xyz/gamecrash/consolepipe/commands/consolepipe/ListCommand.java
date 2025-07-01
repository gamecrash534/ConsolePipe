package xyz.gamecrash.consolepipe.commands.consolepipe;

import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import xyz.gamecrash.consolepipe.console.ConsolePlayer;
import xyz.gamecrash.consolepipe.console.ConsolePlayerManager;
import xyz.gamecrash.consolepipe.ConsolePipe;

import java.util.List;

import static xyz.gamecrash.consolepipe.utils.MessageUtils.*;
import static xyz.gamecrash.consolepipe.config.Messages.*;
import static xyz.gamecrash.consolepipe.config.Permissions.PERMISSION_COMMAND_LIST;

public class ListCommand {
    public LiteralCommandNode<CommandSourceStack> build() {
        ConsolePipe plugin = ConsolePipe.getPlugin();
        ConsolePlayerManager manager = plugin.getConsolePlayerManager();

        return Commands.literal("list")
            .requires(sender -> sender.getSender().hasPermission(PERMISSION_COMMAND_LIST))
            .executes(ctx -> {
                List<ConsolePlayer> players = manager.getPlayers();
                if (players.isEmpty()) {
                    ctx.getSource().getSender().sendMessage(message(returnConfig(ERROR_NO_LIST)));
                    return 1;
                }
                String playerList = players.stream()
                    .map(p -> p.getPlayer().getName())
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("");
                ctx.getSource().getSender().sendMessage(message(returnConfig(PIPE_LIST).replace("%players%", playerList)));
                return 1;
            })
            .build();
    }
}
