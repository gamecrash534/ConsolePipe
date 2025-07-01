package xyz.gamecrash.consolepipe.commands.logs;

import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;

public class ListCommand {
    public LiteralCommandNode<CommandSourceStack> build() {
        return Commands.literal("list")
            .executes(ctx -> {

                return 1;
            })
            .build();
    }
}
