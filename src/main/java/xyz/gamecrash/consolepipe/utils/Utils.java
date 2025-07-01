package xyz.gamecrash.consolepipe.utils;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

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

    public static UUID returnUUID(CommandSender sender) {
        if (sender instanceof Player player) {
            return player.getUniqueId();
        }
        return UUID.fromString("00000000-0000-0000-0000-000000000000");
    }
}
