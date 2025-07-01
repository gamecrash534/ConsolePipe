package xyz.gamecrash.consolepipe.commands.logs;

import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import xyz.gamecrash.consolepipe.ConsolePipe;
import xyz.gamecrash.consolepipe.logs.Log;
import xyz.gamecrash.consolepipe.logs.LogManager;
import xyz.gamecrash.consolepipe.logs.LogUploader;

public class UploadCommand {
    public LiteralCommandNode<CommandSourceStack> build() {
        return Commands.literal("upload")
            .executes(ctx -> {
                LogManager man = ConsolePipe.getPlugin().getLogManager();
                Log log = man.getLatestLog();
                String returnMessage = LogUploader.uploadLog(log).right();
                ctx.getSource().getSender().sendRichMessage("<red>Log Upload Result: <white>" + returnMessage);
                return 1;
            })
            .build();
    }
}
