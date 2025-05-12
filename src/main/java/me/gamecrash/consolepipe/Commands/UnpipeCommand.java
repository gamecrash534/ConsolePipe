package me.gamecrash.consolepipe.Commands;

import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.entity.Player;

import static me.gamecrash.consolepipe.Utils.MessageUtils.*;
import static me.gamecrash.consolepipe.Utils.Messages.MESSAGE_UNPIPED;
import static me.gamecrash.consolepipe.Utils.Permissions.PERMISSION_COMMAND_UNPIPE;
import static me.gamecrash.consolepipe.ConsolePipe.plugin;

public class UnpipeCommand {
    public LiteralCommandNode<CommandSourceStack> build() {
        return Commands.literal("unpipe")
            .requires(sender -> sender.getSender().hasPermission(PERMISSION_COMMAND_UNPIPE))
            .executes(ctx -> {
                plugin.getManager().removePlayer((Player) ctx.getSource().getSender());
                ctx.getSource().getSender().sendMessage(message(returnConfig(MESSAGE_UNPIPED)));
                return 1;
            })
            .build();
    }
}
