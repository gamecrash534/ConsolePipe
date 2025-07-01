package xyz.gamecrash.consolepipe.commands.logs;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import xyz.gamecrash.consolepipe.ConsolePipe;
import xyz.gamecrash.consolepipe.config.Messages;
import xyz.gamecrash.consolepipe.config.Permissions;
import xyz.gamecrash.consolepipe.logs.Log;
import xyz.gamecrash.consolepipe.logs.LogManager;
import xyz.gamecrash.consolepipe.utils.MessageUtils;
import xyz.gamecrash.consolepipe.utils.Utils;

import java.util.concurrent.CompletableFuture;

public class SelectCommand {
    private final ConsolePipe plugin = ConsolePipe.getPlugin();
    private final LogManager logManager = plugin.getLogManager();

    public LiteralCommandNode<CommandSourceStack> build() {
        return Commands.literal("select")
            .requires(source -> source.getSender().hasPermission(Permissions.PERMISSION_COMMAND_LOGS_SELECT))
            .executes(this::argless)
            .then(Commands.argument("log-name", StringArgumentType.greedyString())
                .executes(this::execute)
                .suggests(this::suggestLogs)
            )
            .build();
    }

    private int argless(CommandContext<CommandSourceStack> ctx) {
        MessageUtils.sendConfigMessage(ctx.getSource().getSender(), Messages.HELP_LOG_SELECTED);
        return 1;
    }
    private int execute(CommandContext<CommandSourceStack> ctx) {
        String logName = StringArgumentType.getString(ctx, "log-name");
        Log log = logManager.getLog(logName);
        if (log == null) {
            MessageUtils.sendMessage(ctx.getSource().getSender(), MessageUtils.returnConfig(Messages.ERROR_LOG_NOT_FOUND).replace("%log%", logName));
            return 1;
        }
        logManager.setPlayerLog(Utils.returnUUID(ctx.getSource().getSender()), log);
        MessageUtils.sendMessage(ctx.getSource().getSender(), MessageUtils.returnConfig(Messages.LOGS_SELECTED)
            .replace("%log%", logName)
        );

        return 1;
    }

    private CompletableFuture suggestLogs(CommandContext<CommandSourceStack> ctx, SuggestionsBuilder builder) {
        String input = builder.getRemaining().toLowerCase();
        logManager.getLogNames().stream()
            .filter(log -> log.toLowerCase().startsWith(input))
            .forEach(builder::suggest);
        return builder.buildFuture();
    }
}
