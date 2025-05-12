package me.gamecrash.consolepipe.Commands;

import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;

import static me.gamecrash.consolepipe.Utils.MessageUtils.*;
import static me.gamecrash.consolepipe.Utils.Messages.MESSAGE_RELOAD;
import static me.gamecrash.consolepipe.Utils.Permissions.PERMISSION_COMMAND_RELOAD;
import static me.gamecrash.consolepipe.ConsolePipe.plugin;

public class ReloadCommand {
    public LiteralCommandNode<CommandSourceStack> build() {
        return Commands.literal("reload")
            .requires(sender -> sender.getSender().hasPermission(PERMISSION_COMMAND_RELOAD))
            .executes(ctx -> {
                plugin.reload();
                ctx.getSource().getSender().sendMessage(message(returnConfig(MESSAGE_RELOAD)));
                return 1;
            })
            .build();
    }
}
