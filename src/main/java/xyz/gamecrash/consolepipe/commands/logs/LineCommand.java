package xyz.gamecrash.consolepipe.commands.logs;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import xyz.gamecrash.consolepipe.ConsolePipe;
import xyz.gamecrash.consolepipe.config.Messages;
import xyz.gamecrash.consolepipe.config.Permissions;
import xyz.gamecrash.consolepipe.logs.Log;
import xyz.gamecrash.consolepipe.logs.LogManager;
import xyz.gamecrash.consolepipe.logs.LogReader;
import xyz.gamecrash.consolepipe.utils.MessageBuilder;
import xyz.gamecrash.consolepipe.utils.MessageUtils;
import xyz.gamecrash.consolepipe.utils.Utils;

import java.io.IOException;

public class LineCommand {
    LogManager logManager = ConsolePipe.getPlugin().getLogManager();

    public LiteralCommandNode<CommandSourceStack> build() {
        return Commands.literal("line")
            .requires(source -> source.getSender().hasPermission(Permissions.PERMISSION_COMMAND_LOGS_INFO))
            .then(Commands.argument("line", IntegerArgumentType.integer(0))
                .executes(this::execute)
            )
            .build();
    }

    private int execute(CommandContext<CommandSourceStack> ctx) {
        int line = ctx.getArgument("line", Integer.class);

        Log log = logManager.getPlayerLog(Utils.returnUUID(ctx.getSource().getSender()));
        if (log == null) {
            MessageUtils.sendConfigMessage(ctx.getSource().getSender(), Messages.ERROR_NO_LOG_SELECTED);
            return 1;
        }

        try {
            String lineContents = new LogReader(log).readLine(line);
            MessageUtils.sendRaw(ctx.getSource().getSender(), MessageBuilder.fromConfig(Messages.LOGS_LINE)
                .prefix()
                .replace("line", String.valueOf(line))
                .replace("contents", lineContents)
                .toString()
            );

        } catch (IOException e) {
            MessageUtils.sendConfigMessage(ctx.getSource().getSender(), Messages.ERROR_LOG_READ_FAILED);
        }

        return 1;
    }
}
