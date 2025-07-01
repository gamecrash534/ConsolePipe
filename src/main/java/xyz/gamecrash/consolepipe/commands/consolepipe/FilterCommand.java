package xyz.gamecrash.consolepipe.commands.consolepipe;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import xyz.gamecrash.consolepipe.console.ConsolePlayer;
import xyz.gamecrash.consolepipe.console.ConsolePlayerManager;
import xyz.gamecrash.consolepipe.ConsolePipe;
import org.bukkit.entity.Player;

import static xyz.gamecrash.consolepipe.utils.MessageUtils.*;
import static xyz.gamecrash.consolepipe.config.Permissions.*;
import static xyz.gamecrash.consolepipe.utils.Utils.checkSenderIsPlayer;
import static xyz.gamecrash.consolepipe.utils.Utils.resolveArgumentPlayer;
import static xyz.gamecrash.consolepipe.config.Messages.*;

public class FilterCommand {
    private final ConsolePipe plugin = ConsolePipe.getPlugin();
    private final ConsolePlayerManager manager = plugin.getConsolePlayerManager();

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
        if (!checkPlayer((Player) ctx.getSource().getSender())) {
            ctx.getSource().getSender().sendMessage(message(returnConfig(ERROR_NOT_REGISTERED)));
            return 1;
        }
        if (!checkSenderIsPlayer(ctx.getSource().getSender())) {
            ctx.getSource().getSender().sendMessage(message(returnConfig(ERROR_NO_PLAYER)));
            return 1;
        }
        ConsolePlayer player = manager.getPlayer((Player) ctx.getSource().getSender());
        String regex = StringArgumentType.getString(ctx, "regex");
        player.setDenyReg(regex);
        ctx.getSource().getSender().sendMessage(message(returnConfig(PIPE_FILTER_SET)));
        return 1;
    }
    private int handleCommand(Player targetPlayer, CommandContext<CommandSourceStack> ctx) {
        if (!checkPlayer(targetPlayer))  {
            ctx.getSource().getSender().sendMessage(message(returnConfig(ERROR_NOT_REGISTERED)));
            return 1;
        }
        ConsolePlayer player = manager.getPlayer(targetPlayer);
        String regex = StringArgumentType.getString(ctx, "player_reg");
        player.setDenyReg(regex);
        ctx.getSource().getSender().sendMessage(message(returnConfig(PIPE_FILTER_SET_PLAYER)));
        return 1;
    }

    private boolean checkPlayer(Player targetPlayer) {
        if (targetPlayer == null) return false;
        return manager.contains(targetPlayer);
    }
}
