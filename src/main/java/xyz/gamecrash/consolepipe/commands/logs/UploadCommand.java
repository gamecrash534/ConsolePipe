package xyz.gamecrash.consolepipe.commands.logs;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import it.unimi.dsi.fastutil.Pair;
import xyz.gamecrash.consolepipe.ConsolePipe;
import xyz.gamecrash.consolepipe.config.ConfigEntries;
import xyz.gamecrash.consolepipe.config.Messages;
import xyz.gamecrash.consolepipe.logs.Log;
import xyz.gamecrash.consolepipe.logs.LogManager;
import xyz.gamecrash.consolepipe.logs.LogUploader;
import xyz.gamecrash.consolepipe.utils.MessageUtils;
import xyz.gamecrash.consolepipe.utils.Utils;

public class UploadCommand {
    ConsolePipe plugin = ConsolePipe.getPlugin();
    LogManager logManager = plugin.getLogManager();

    public LiteralCommandNode<CommandSourceStack> build() {
        return Commands.literal("upload")
            .executes(this::execute)
            .build();
    }

    private int execute(CommandContext<CommandSourceStack> ctx) {
        Log log = logManager.getPlayerLog(Utils.returnUUID(ctx.getSource().getSender()));
        if (log == null) {
            MessageUtils.sendConfigMessage(ctx.getSource().getSender(), Messages.ERROR_NO_LOG_SELECTED);
            return 1;
        }
        Pair<Boolean, String> uploadResult = LogUploader.uploadLog(log);

        if (uploadResult.first()) {
            MessageUtils.sendMessage(ctx.getSource().getSender(), MessageUtils.returnConfig(Messages.LOGS_UPLOADED)
                .replace("%log", log.getName())
                .replace("%link%",
                    plugin.getConfig().getString(ConfigEntries.BASE_URL) + uploadResult.second().replaceAll(".*\"key\"\\s*:\\s*\"([^\"]+)\".*", "$1")
                )
            );
        } else {
            MessageUtils.sendMessage(ctx.getSource().getSender(), MessageUtils.returnConfig(Messages.ERROR_LOG_UPLOAD_FAILED)
                .replace("%error%", uploadResult.second())
            );
        }
        return 1;
    }
}
