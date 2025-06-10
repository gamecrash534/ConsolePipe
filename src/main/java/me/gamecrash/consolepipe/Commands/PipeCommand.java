package me.gamecrash.consolepipe.Commands;

import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import me.gamecrash.consolepipe.Console.ConsolePlayerManager;
import me.gamecrash.consolepipe.ConsolePipe;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.gamecrash.consolepipe.Utils.MessageUtils.*;
import static me.gamecrash.consolepipe.Utils.Messages.*;
import static me.gamecrash.consolepipe.Utils.Permissions.*;
import static me.gamecrash.consolepipe.Utils.Utils.checkSenderIsPlayer;
import static me.gamecrash.consolepipe.Utils.Utils.resolveArgumentPlayer;

public class PipeCommand {
    private final ConsolePipe plugin = ConsolePipe.getPlugin();
    private final ConsolePlayerManager manager = plugin.getManager();

    public LiteralCommandNode<CommandSourceStack> build() {
        return Commands.literal("pipe")
            .requires(sender -> sender.getSender().hasPermission(PERMISSION_COMMAND_PIPE))
            .executes(ctx -> handleCommand(ctx.getSource().getSender()))
            .then(Commands.argument("player", ArgumentTypes.player())
                .requires(sender -> sender.getSender().hasPermission(PERMISSION_COMMAND_PIPE_PLAYER))
                .executes(ctx -> {
                    Player targetPlayer = resolveArgumentPlayer(ctx);
                    return handleCommand(targetPlayer, ctx.getSource().getSender());
                })
            )
            .build();
    }

    private int handleCommand(CommandSender player) {
        if (!checkSenderIsPlayer(player)) {
            player.sendMessage(message(returnConfig(MESSAGE_NO_PLAYER)));
            return 1;
        }
        if (manager.contains((Player) player)) {
            player.sendMessage(message(returnConfig(MESSAGE_ALREADY_PIPED)));
            return 1;
        }
        manager.addPlayer((Player) player);
        player.sendMessage(message(returnConfig(MESSAGE_PIPED)));
        return 1;
    }
    private int handleCommand(Player targetPlayer, CommandSender sender) {
        if (manager.contains(targetPlayer)) {
            sender.sendMessage(message(returnConfig(MESSAGE_PLAYER_ALREADY_PIPED)
                .replace("%player%", targetPlayer.getName())
            ));
            return 1;
        }
        manager.addPlayer(targetPlayer);
        sender.sendMessage(message(returnConfig(MESSAGE_PIPED_PLAYER)
            .replace("%player%", targetPlayer.getName())
        ));
        return 1;
    }
}