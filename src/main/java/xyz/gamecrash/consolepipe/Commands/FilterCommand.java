package xyz.gamecrash.consolepipe.Commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import xyz.gamecrash.consolepipe.Console.ConsolePlayer;
import xyz.gamecrash.consolepipe.Console.ConsolePlayerManager;
import xyz.gamecrash.consolepipe.ConsolePipe;
import org.bukkit.entity.Player;

import static xyz.gamecrash.consolepipe.Utils.MessageUtils.*;
import static xyz.gamecrash.consolepipe.Utils.Permissions.*;
import static xyz.gamecrash.consolepipe.Utils.Utils.checkSenderIsPlayer;
import static xyz.gamecrash.consolepipe.Utils.Utils.resolveArgumentPlayer;
import static xyz.gamecrash.consolepipe.Utils.Messages.*;

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
        if (!checkPlayer((Player) ctx.getSource().getSender())) {
            ctx.getSource().getSender().sendMessage(message(returnConfig(MESSAGE_NOT_REGISTERED)));
            return 1;
        }
        if (!checkSenderIsPlayer(ctx.getSource().getSender())) {
            ctx.getSource().getSender().sendMessage(message(returnConfig(MESSAGE_NO_PLAYER)));
            return 1;
        }
        ConsolePlayer player = manager.getPlayer((Player) ctx.getSource().getSender());
        String regex = StringArgumentType.getString(ctx, "regex");
        player.setDenyReg(regex);
        ctx.getSource().getSender().sendMessage(message(returnConfig(MESSAGE_FILTER_SET)));
        return 1;
    }
    private int handleCommand(Player targetPlayer, CommandContext<CommandSourceStack> ctx) {
        if (!checkPlayer(targetPlayer))  {
            ctx.getSource().getSender().sendMessage(message(returnConfig(MESSAGE_NOT_REGISTERED)));
            return 1;
        }
        ConsolePlayer player = manager.getPlayer(targetPlayer);
        String regex = StringArgumentType.getString(ctx, "player_reg");
        player.setDenyReg(regex);
        ctx.getSource().getSender().sendMessage(message(returnConfig(MESSAGE_FILTER_SET_OTHER)));
        return 1;
    }

    private boolean checkPlayer(Player targetPlayer) {
        if (targetPlayer == null) return false;
        return manager.contains(targetPlayer);
    }
}
