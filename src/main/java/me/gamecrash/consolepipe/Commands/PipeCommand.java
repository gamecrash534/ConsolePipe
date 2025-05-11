package me.gamecrash.consolepipe.Commands;

import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.entity.Player;

import static me.gamecrash.consolepipe.Utils.MessageUtils.*;
import static me.gamecrash.consolepipe.Utils.Messages.MESSAGE_PIPED;
import static me.gamecrash.consolepipe.Utils.Permissions.PERMISSION_COMMAND_PIPE;

public class PipeCommand {
    public LiteralCommandNode<CommandSourceStack> build() {
        return Commands.literal("pipe")
            .requires(sender -> sender.getSender().hasPermission(PERMISSION_COMMAND_PIPE))
            .executes(ctx -> {
                plugin.getCache().addPlayer((Player) ctx.getSource().getSender(), "", "");
                ctx.getSource().getSender().sendMessage(message(returnConfig(MESSAGE_PIPED)));
                return 1;
            })
            .build();
    }
}
