package me.gamecrash.consolepipe.Commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.entity.Player;

import static me.gamecrash.consolepipe.Utils.MessageUtils.*;
import static me.gamecrash.consolepipe.Utils.Permissions.*;
import static me.gamecrash.consolepipe.Utils.Messages.*;

public class ConsolePipeCommand {
    public LiteralCommandNode<CommandSourceStack> build() {
        LiteralArgumentBuilder<CommandSourceStack> builder = Commands.literal("consolepipe")
            .requires(sender -> sender.getSender().hasPermission(PERMISSION_COMMAND))
            .executes(ctx -> {
                ctx.getSource().getSender().sendMessage(message(returnConfig(MESSAGE_BASE)));
                return 1;
            })
            .then(pipeCommand())
            .then(unpipeCommand())
            ;

        return builder.build();
    }
    public LiteralCommandNode<CommandSourceStack> pipeCommand() {
        return Commands.literal("pipe")
            .requires(sender -> sender.getSender().hasPermission(PERMISSION_COMMAND_PIPE))
            .executes(ctx -> {
                plugin.getCache().addPlayer((Player) ctx.getSource().getSender(), "", "");
                ctx.getSource().getSender().sendMessage(message(returnConfig(MESSAGE_PIPED)));
                return 1;
            })
            .build();
    }
    public LiteralCommandNode<CommandSourceStack> unpipeCommand() {
        return Commands.literal("unpipe")
            .requires(sender -> sender.getSender().hasPermission(PERMISSION_COMMAND_UNPIPE))
            .executes(ctx -> {
                plugin.getCache().removePlayer((Player) ctx.getSource().getSender());
                ctx.getSource().getSender().sendMessage(message(returnConfig(MESSAGE_UNPIPED)));
                return 1;
            })
            .build();
    }
}
