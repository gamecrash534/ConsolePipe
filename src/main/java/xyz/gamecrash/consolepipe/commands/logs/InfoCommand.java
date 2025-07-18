package xyz.gamecrash.consolepipe.commands.logs;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import xyz.gamecrash.consolepipe.ConsolePipe;
import xyz.gamecrash.consolepipe.config.Messages;
import xyz.gamecrash.consolepipe.config.Permissions;
import xyz.gamecrash.consolepipe.logs.Log;
import xyz.gamecrash.consolepipe.logs.LogManager;
import xyz.gamecrash.consolepipe.utils.MessageBuilder;
import xyz.gamecrash.consolepipe.utils.MessageUtils;
import xyz.gamecrash.consolepipe.utils.Utils;

public class InfoCommand {
    private final ConsolePipe plugin = ConsolePipe.getPlugin();
    private final LogManager logManager = plugin.getLogManager();

    public LiteralCommandNode<CommandSourceStack> build() {
        return Commands.literal("info")
            .requires(source -> source.getSender().hasPermission(Permissions.PERMISSION_COMMAND_LOGS_INFO))
            .executes(this::execute)
            .build();
    }

    private int execute(CommandContext<CommandSourceStack> ctx) {
        Log log = logManager.getPlayerLog(Utils.returnUUID(ctx.getSource().getSender()));
        MessageUtils.sendRaw(ctx.getSource().getSender(), MessageBuilder.fromConfig(Messages.LOGS_INFO)
            .prefix()
            .replace("name", log.getName())
            .replace("size", String.valueOf(log.getFileSize()))
            .replace("modified", log.getLastModified().toString())
            .toString()
        );

        return 1;
    }
}
