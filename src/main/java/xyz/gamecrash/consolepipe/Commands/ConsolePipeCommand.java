package xyz.gamecrash.consolepipe.Commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;

import static xyz.gamecrash.consolepipe.Utils.MessageUtils.*;
import static xyz.gamecrash.consolepipe.Utils.Permissions.*;

public class ConsolePipeCommand {
    public LiteralCommandNode<CommandSourceStack> build() {
        LiteralArgumentBuilder<CommandSourceStack> builder = Commands.literal("consolepipe")
            .requires(sender -> sender.getSender().hasPermission(PERMISSION_COMMAND))
            .executes(ctx -> {
                ctx.getSource().getSender().sendMessage(message("<white>ConsolePipe v${project.version} by game.crash"));
                return 1;
            })
            .then(new PipeCommand().build())
            .then(new UnpipeCommand().build())
            .then(new ReloadCommand().build())
            .then(new ListCommand().build())
            .then(new FilterCommand().build())
            ;

        return builder.build();
    }

}
