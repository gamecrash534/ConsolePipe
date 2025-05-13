package me.gamecrash.consolepipe.Commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import me.gamecrash.consolepipe.Console.ConsolePlayerManager;
import me.gamecrash.consolepipe.ConsolePipe;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.gamecrash.consolepipe.Utils.MessageUtils.*;
import static me.gamecrash.consolepipe.Utils.Messages.*;
import static me.gamecrash.consolepipe.Utils.Permissions.*;
import static me.gamecrash.consolepipe.Utils.Utils.checkPlayer;

public class UnpipeCommand {
    private final ConsolePipe plugin = ConsolePipe.getPlugin();
    private final ConsolePlayerManager manager = plugin.getManager();

    public LiteralCommandNode<CommandSourceStack> build() {
        return Commands.literal("unpipe")
            .requires(sender -> sender.getSender().hasPermission(PERMISSION_COMMAND_PIPE))
            .executes(ctx -> handleCommand((Player) ctx.getSource().getSender()))
            .then(Commands.argument("player", ArgumentTypes.player())
                .requires(sender -> sender.getSender().hasPermission(PERMISSION_COMMAND_PIPE_PLAYER))
                .executes(ctx -> {
                    Player targetPlayer = resolvePlayer(ctx);
                    return handleCommand(targetPlayer, ctx.getSource().getSender());
                })
            )
            .build();
    }

    private int handleCommand(Player player) {
        if (!checkPlayer(player)) {
            player.sendMessage(message(returnConfig(MESSAGE_NO_PLAYER)));
            return 1;
        }
        if (!manager.contains(player)) {
            player.sendMessage(message(returnConfig(MESSAGE_ALREADY_UNPIPED)));
            return 1;
        }
        manager.removePlayer(player);
        player.sendMessage(message(returnConfig(MESSAGE_UNPIPED)));
        return 1;
    }
    private int handleCommand(Player targetPlayer, CommandSender sender) {
        if (!manager.contains(targetPlayer)) {
            sender.sendMessage(message(returnConfig(MESSAGE_PLAYER_ALREADY_UNPIPED)
                .replace("%player%", targetPlayer.getName())
            ));
            return 1;
        }
        manager.removePlayer(targetPlayer);
        sender.sendMessage(message(returnConfig(MESSAGE_UNPIPED_PLAYER)
            .replace("%player%", targetPlayer.getName())
        ));
        return 1;
    }

    private Player resolvePlayer(CommandContext<CommandSourceStack> ctx) {
        try {
            return ctx.getArgument("player", PlayerSelectorArgumentResolver.class)
                .resolve(ctx.getSource())
                .getFirst();
        } catch (CommandSyntaxException e) {
            return null;
        }
    }
}
