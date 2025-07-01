package xyz.gamecrash.consolepipe.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import xyz.gamecrash.consolepipe.commands.consolepipe.*;

import static xyz.gamecrash.consolepipe.utils.MessageUtils.*;
import static xyz.gamecrash.consolepipe.config.Permissions.*;

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
            .then(new LogsCommand().build())
            ;

        return builder.build();
    }
}
