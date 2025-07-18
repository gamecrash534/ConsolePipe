package xyz.gamecrash.consolepipe.commands;

import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import xyz.gamecrash.consolepipe.commands.logs.*;
import xyz.gamecrash.consolepipe.config.Messages;
import xyz.gamecrash.consolepipe.utils.MessageUtils;

import static xyz.gamecrash.consolepipe.utils.MessageUtils.message;
import static xyz.gamecrash.consolepipe.config.Permissions.PERMISSION_COMMAND;

public class LogsCommand {
    public LiteralCommandNode<CommandSourceStack> build() {
        return Commands.literal("logs")
            .requires(sender -> sender.getSender().hasPermission(PERMISSION_COMMAND))
            .executes(ctx -> {
                MessageUtils.sendConfigMessage(ctx.getSource().getSender(), Messages.HELP_USAGE_LOGS);
                return 1;
            })
            .then(new ListCommand().build())
            .then(new SelectCommand().build())
            .then(new UploadCommand().build())
            .then(new SearchCommand().build())
            .then(new InfoCommand().build())
            .then(new LineCommand().build())
            .build();
    }
}
