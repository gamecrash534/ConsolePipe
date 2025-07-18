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

import java.util.List;
import java.util.stream.Collectors;

public class ListCommand {
    private final LogManager logManager = ConsolePipe.getPlugin().getLogManager();

    public LiteralCommandNode<CommandSourceStack> build() {
        return Commands.literal("list")
            .requires(source -> source.getSender().hasPermission(Permissions.PERMISSION_COMMAND_LOGS_LIST))
            .executes(this::execute)
            .build();
    }

    private int execute(CommandContext<CommandSourceStack> ctx) {
        MessageUtils.sendRaw(ctx.getSource().getSender(), MessageBuilder.fromConfig(Messages.LOGS_LIST)
            .prefix()
            .replace("%logs%", logManager.getLogNames() != null ? String.join("<white>,<newline>", logManager.getLogNames()) : "No logs found")
            .toString()
        );
        return 1;
    }

    private List<String> listLogs(String type, String nameRegex) {
        List<String> logs = logManager.getLogNames();
        return logs.stream()
            .filter(log -> type == null || log.contains("type"))
            .filter(log -> nameRegex == null || log.matches(nameRegex))
            .collect(Collectors.toList());
    }
}
