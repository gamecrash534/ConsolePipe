package xyz.gamecrash.consolepipe.commands;

import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import xyz.gamecrash.consolepipe.commands.logs.*;

import static xyz.gamecrash.consolepipe.utils.MessageUtils.message;
import static xyz.gamecrash.consolepipe.config.Permissions.PERMISSION_COMMAND;

public class LogsCommand {
    public LiteralCommandNode<CommandSourceStack> build() {
        return Commands.literal("logs")
            .requires(sender -> sender.getSender().hasPermission(PERMISSION_COMMAND))
            .executes(ctx -> {
                ctx.getSource().getSender().sendMessage(message("<white>ConsolePipe v${project.version} by game.crash"));
                return 1;
            })
            .then(new ListCommand().build())
            .then(new SelectCommand().build())
            .then(new UploadCommand().build())
            .build();
    }
}
