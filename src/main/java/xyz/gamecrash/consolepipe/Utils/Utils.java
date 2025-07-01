package xyz.gamecrash.consolepipe.Utils;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Utils {
    public static boolean checkSenderIsPlayer(CommandSender sender) {
        return sender instanceof Player;
    }
    public static Player resolveArgumentPlayer(CommandContext<CommandSourceStack> ctx) {
        try {
            return ctx.getArgument("player", PlayerSelectorArgumentResolver.class)
                .resolve(ctx.getSource())
                .getFirst();
        } catch (CommandSyntaxException e) {
            return null;
        }
    }
}
