package xyz.gamecrash.consolepipe.commands.consolepipe;

import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import xyz.gamecrash.consolepipe.console.ConsolePlayerManager;
import xyz.gamecrash.consolepipe.ConsolePipe;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static xyz.gamecrash.consolepipe.utils.MessageUtils.*;
import static xyz.gamecrash.consolepipe.config.Messages.*;
import static xyz.gamecrash.consolepipe.config.Permissions.*;
import static xyz.gamecrash.consolepipe.utils.Utils.checkSenderIsPlayer;
import static xyz.gamecrash.consolepipe.utils.Utils.resolveArgumentPlayer;

public class UnpipeCommand {
    private final ConsolePipe plugin = ConsolePipe.getPlugin();
    private final ConsolePlayerManager manager = plugin.getConsolePlayerManager();

    public LiteralCommandNode<CommandSourceStack> build() {
        return Commands.literal("unpipe")
            .requires(sender -> sender.getSender().hasPermission(PERMISSION_COMMAND_UNPIPE))
            .executes(ctx -> handleCommand(ctx.getSource().getSender()))
            .then(Commands.argument("player", ArgumentTypes.player())
                .requires(sender -> sender.getSender().hasPermission(PERMISSION_COMMAND_UNPIPE_PLAYER))
                .executes(ctx -> {
                    Player targetPlayer = resolveArgumentPlayer(ctx);
                    return handleCommand(targetPlayer, ctx.getSource().getSender());
                })
            )
            .build();
    }

    private int handleCommand(CommandSender player) {
        if (!checkSenderIsPlayer(player)) {
            player.sendMessage(message(returnConfig(ERROR_NO_PLAYER)));
            return 1;
        }
        if (!manager.contains((Player) player)) {
            player.sendMessage(message(returnConfig(PIPE_ALREADY_UNPIPED)));
            return 1;
        }
        manager.removePlayer((Player) player);
        player.sendMessage(message(returnConfig(PIPE_UNPIPED)));
        return 1;
    }
    private int handleCommand(Player targetPlayer, CommandSender sender) {
        if (!manager.contains(targetPlayer)) {
            sender.sendMessage(message(returnConfig(PIPE_PLAYER_ALREADY_UNPIPED)
                .replace("%player%", targetPlayer.getName())
            ));
            return 1;
        }
        manager.removePlayer(targetPlayer);
        sender.sendMessage(message(returnConfig(PIPE_UNPIPED_PLAYER)
            .replace("%player%", targetPlayer.getName())
        ));
        return 1;
    }
}
