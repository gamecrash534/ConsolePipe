package xyz.gamecrash.consolepipe.commands.logs;

import com.mojang.brigadier.arguments.IntegerArgumentType;
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
import xyz.gamecrash.consolepipe.utils.MessageBuilder;
import xyz.gamecrash.consolepipe.utils.MessageUtils;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ListCommand {
    private final LogManager logManager = ConsolePipe.getPlugin().getLogManager();
    private final int itemsPerPage = ConsolePipe.getPlugin().getConfig().getInt("list-items-per-page", 10);

    public LiteralCommandNode<CommandSourceStack> build() {
        return Commands.literal("list")
            .requires(source -> source.getSender().hasPermission(Permissions.PERMISSION_COMMAND_LOGS_LIST))
            .executes(ctx -> execute(ctx, 1, null, null))
            .then(Commands.literal("type")
                .then(Commands.argument("type", StringArgumentType.word())
                    .suggests((c, b) -> {
                        b.suggest("log");
                        b.suggest("error");
                        return b.buildFuture();
                    })
                    .executes(ctx -> execute(ctx, 1, ctx.getArgument("type", String.class), null))
                    .then(Commands.argument("page", IntegerArgumentType.integer(1))
                        .executes(ctx -> execute(ctx, ctx.getArgument("page", Integer.class), ctx.getArgument("type", String.class), null))
                    )
                )
            )
            .then(Commands.literal("reg")
                .then(Commands.argument("nameReg", StringArgumentType.string())
                    .executes(ctx -> execute(ctx, 1, null, ctx.getArgument("nameReg", String.class)))
                    .then(Commands.argument("page", IntegerArgumentType.integer(1))
                        .executes(ctx -> execute(ctx, ctx.getArgument("page", Integer.class), null, ctx.getArgument("nameReg", String.class)))
                    )
                )
            )
            .then(Commands.argument("page", IntegerArgumentType.integer(1))
                .executes(ctx -> execute(ctx, ctx.getArgument("page", Integer.class), null, null))
            )
            .build();
    }

    private int execute(CommandContext<CommandSourceStack> ctx, int page, String type, String nameReg) {
        List<Log> logs = logManager.getLogs();

        if (type != null) {
            String typeLower = type.toLowerCase();
            if (!typeLower.equals("log") && !typeLower.equals("error")) {
                MessageUtils.sendRaw(ctx.getSource().getSender(), "§cUngültiger Typ! Nur 'log' oder 'error' sind erlaubt.");
                return 0;
            }
            logs = logs.stream()
                .filter(log -> typeLower.equals(log.getType()))
                .collect(Collectors.toList());
        }

        if (nameReg != null) {
            Pattern pattern = Pattern.compile(nameReg);
            logs = logs.stream()
                .filter(log -> pattern.matcher(log.getName()).find())
                .collect(Collectors.toList());
        }

        int totalPages = Math.max(1, (int) Math.ceil((double) logs.size() / itemsPerPage));
        page = Math.max(1, Math.min(page, totalPages));

        MessageBuilder output = MessageBuilder.fromConfig(Messages.LOGS_LIST_HEADER)
            .replaceConfig("prefix", Messages.MESSAGE_PREFIX)
            .replace("page", String.valueOf(page))
            .replace("pages", String.valueOf(totalPages));

        if (logs.isEmpty()) {
            output.append(MessageBuilder.fromConfig(Messages.LOGS_LIST_NO_LOGS));
        } else {
            int start = (page - 1) * itemsPerPage;
            int end = Math.min(start + itemsPerPage, logs.size());
            for (int i = start; i < end; i++) {
                Log log = logs.get(i);
                output.append(
                    MessageBuilder.fromConfig(Messages.LOGS_LIST_ITEM)
                        .append("<newline>")
                        .replace("log", log.getName())
                        .replace("size", String.valueOf(log.getFileSize()))
                );
            }
        }

        output.append(MessageBuilder.fromConfig(Messages.LOGS_LIST_FOOTER));
        MessageUtils.sendRaw(ctx.getSource().getSender(), output.toString());
        return 1;
    }
}