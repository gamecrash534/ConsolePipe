package me.gamecrash.consolepipe.Commands;

import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import me.gamecrash.consolepipe.Console.ConsolePlayerManager;
import me.gamecrash.consolepipe.ConsolePipe;
import org.bukkit.entity.Player;

import static me.gamecrash.consolepipe.Utils.MessageUtils.*;
import static me.gamecrash.consolepipe.Utils.Messages.MESSAGE_ALREADY_PIPED;
import static me.gamecrash.consolepipe.Utils.Messages.MESSAGE_PIPED;
import static me.gamecrash.consolepipe.Utils.Permissions.PERMISSION_COMMAND_PIPE;

public class PipeCommand {
    private final ConsolePipe plugin = ConsolePipe.getPlugin();
    private ConsolePlayerManager manager = plugin.getManager();

    public LiteralCommandNode<CommandSourceStack> build() {
        return Commands.literal("pipe")
            .requires(sender -> sender.getSender().hasPermission(PERMISSION_COMMAND_PIPE))
            .executes(ctx -> {
                if (manager.contains((Player) ctx.getSource().getSender())) {
                    ctx.getSource().getSender().sendMessage(message(returnConfig(MESSAGE_ALREADY_PIPED)));
                    return 1;
                }

                plugin.getManager().addPlayer((Player) ctx.getSource().getSender());
                ctx.getSource().getSender().sendMessage(message(returnConfig(MESSAGE_PIPED)));
                return 1;
            })
            .build();
    }
}
