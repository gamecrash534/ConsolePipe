package xyz.gamecrash.consolepipe.commands.logs;

import com.mojang.brigadier.arguments.StringArgumentType;
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
import java.util.List;

public class SearchCommand {
    private final LogManager logManager = ConsolePipe.getPlugin().getLogManager();

    public LiteralCommandNode<CommandSourceStack> build() {
        return Commands.literal("search")
            .requires(source -> source.getSender().hasPermission(Permissions.PERMISSION_COMMAND_LOGS_SEARCH))
            .then(Commands.argument("regex", StringArgumentType.greedyString())
                .executes(this::execute)
            )
            .build();
    }

    private int execute(CommandContext<CommandSourceStack> ctx) {
        Log log = logManager.getPlayerLog(Utils.returnUUID(ctx.getSource().getSender()));
        if (log == null) {
            MessageUtils.sendConfigMessage(ctx.getSource().getSender(), Messages.ERROR_NO_LOG_SELECTED);
            return 1;
        }

        String regex = ctx.getArgument("regex", String.class);
        try {
            List<String> result = new LogReader(log).search(regex);
            if (result.isEmpty()) {
                MessageUtils.sendConfigMessage(ctx.getSource().getSender(), Messages.ERROR_LOG_SEARCH_NO_RESULTS);
            } else {
                MessageUtils.sendMessage(ctx.getSource().getSender(), MessageBuilder.fromConfig(Messages.LOGS_SEARCH_RESULT)
                    .prefix()
                    .replace("log", log.getName())
                    .replace("results", String.join("<newline><newline>", result)
                    )
                    .toString()
                );
            }
        } catch (IOException e) {
            MessageUtils.sendConfigMessage(ctx.getSource().getSender(), Messages.ERROR_LOG_READ_FAILED);
            return 1;
        }
        return 1;
    }
}
