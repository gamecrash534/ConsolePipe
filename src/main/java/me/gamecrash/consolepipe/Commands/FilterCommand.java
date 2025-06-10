package me.gamecrash.consolepipe.Commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import me.gamecrash.consolepipe.Console.ConsolePlayer;
import me.gamecrash.consolepipe.Console.ConsolePlayerManager;
import me.gamecrash.consolepipe.ConsolePipe;
import org.bukkit.entity.Player;

import static me.gamecrash.consolepipe.Utils.MessageUtils.*;
import static me.gamecrash.consolepipe.Utils.Permissions.*;
import static me.gamecrash.consolepipe.Utils.Utils.resolveArgumentPlayer;
import static me.gamecrash.consolepipe.Utils.Messages.*;

public class FilterCommand {
    private final ConsolePipe plugin = ConsolePipe.getPlugin();
    private final ConsolePlayerManager manager = plugin.getManager();

    public LiteralCommandNode<CommandSourceStack> build() {
        return Commands.literal("filter")
            .requires(sender -> sender.getSender().hasPermission(PERMISSION_COMMAND_FILTER))
            .then(Commands.argument("regex", StringArgumentType.greedyString())
                .executes(ctx -> handleCommand(ctx))
            )
            .then(Commands.argument("player", ArgumentTypes.player())
                .requires(sender -> sender.getSender().hasPermission(PERMISSION_COMMAND_FILTER_PLAYER))
                .then(Commands.argument("player_reg", StringArgumentType.greedyString())
                    .executes(ctx -> handleCommand(resolveArgumentPlayer(ctx), ctx))
                )
            )
            .build();
    }

    private int handleCommand(CommandContext<CommandSourceStack> ctx ) {
        ConsolePlayer player = manager.getPlayer((Player) ctx.getSource().getSender());
        String regex = StringArgumentType.getString(ctx, "regex");
        player.setDenyReg(regex);
        ctx.getSource().getSender().sendMessage(message(returnConfig(MESSAGE_FILTER_SET)));
        return 1;
    }
    private int handleCommand(Player targetPlayer, CommandContext<CommandSourceStack> ctx) {
        ConsolePlayer player = manager.getPlayer(targetPlayer);
        String regex = StringArgumentType.getString(ctx, "player_reg");
        player.setDenyReg(regex);
        ctx.getSource().getSender().sendMessage(message(returnConfig(MESSAGE_FILTER_SET_OTHER).replace("%player%", targetPlayer.getName())));
        return 1;
    }
}
