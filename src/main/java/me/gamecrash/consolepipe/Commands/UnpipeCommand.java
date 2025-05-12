package me.gamecrash.consolepipe.Commands;

import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import me.gamecrash.consolepipe.Console.ConsolePlayerManager;
import me.gamecrash.consolepipe.ConsolePipe;
import org.bukkit.entity.Player;

import static me.gamecrash.consolepipe.Utils.MessageUtils.*;
import static me.gamecrash.consolepipe.Utils.Messages.*;
import static me.gamecrash.consolepipe.Utils.Permissions.PERMISSION_COMMAND_UNPIPE;

public class UnpipeCommand {
    private final ConsolePipe plugin = ConsolePipe.getPlugin();
    private final ConsolePlayerManager manager = plugin.getManager();

    public LiteralCommandNode<CommandSourceStack> build() {
        return Commands.literal("unpipe")
            .requires(sender -> sender.getSender().hasPermission(PERMISSION_COMMAND_UNPIPE))
            .executes(ctx -> {
                if (!manager.contains((Player) ctx.getSource().getSender())) {
                    ctx.getSource().getSender().sendMessage(message(returnConfig(MESSAGE_ALREADY_UNPIPED)));
                    return 1;
                }

                plugin.getManager().removePlayer((Player) ctx.getSource().getSender());
                ctx.getSource().getSender().sendMessage(message(returnConfig(MESSAGE_UNPIPED)));
                return 1;
            })
            .build();
    }
}
