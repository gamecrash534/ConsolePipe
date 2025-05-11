package me.gamecrash.consolepipe.Commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;

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
            .then(new PipeCommand().build())
            .then(new UnpipeCommand().build());
            ;

        return builder.build();
    }

}
