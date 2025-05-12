package me.gamecrash.consolepipe.Commands;

import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import me.gamecrash.consolepipe.Console.ConsolePlayer;
import me.gamecrash.consolepipe.Console.ConsolePlayerManager;
import me.gamecrash.consolepipe.ConsolePipe;

import java.util.List;

import static me.gamecrash.consolepipe.Utils.MessageUtils.*;
import static me.gamecrash.consolepipe.Utils.Messages.*;
import static me.gamecrash.consolepipe.Utils.Permissions.PERMISSION_COMMAND_LIST;

public class ListCommand {
    public LiteralCommandNode<CommandSourceStack> build() {
        ConsolePipe plugin = ConsolePipe.getPlugin();
        ConsolePlayerManager manager = plugin.getManager();

        return Commands.literal("list")
            .requires(sender -> sender.getSender().hasPermission(PERMISSION_COMMAND_LIST))
            .executes(ctx -> {
                List<ConsolePlayer> players = manager.getPlayers();
                if (players.isEmpty()) {
                    ctx.getSource().getSender().sendMessage(message(returnConfig(MESSAGE_NO_LIST)));
                    return 1;
                }
                String playerList = players.stream()
                    .map(p -> p.getPlayer().getName())
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("");
                ctx.getSource().getSender().sendMessage(message(returnConfig(MESSAGE_LIST).replace("%players%", playerList)));
                return 1;
            })
            .build();
    }
}
