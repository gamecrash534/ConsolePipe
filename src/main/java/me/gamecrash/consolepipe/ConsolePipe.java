package me.gamecrash.consolepipe;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import me.gamecrash.consolepipe.Commands.ConsolePipeCommand;
import me.gamecrash.consolepipe.Console.ConsoleFilter;
import me.gamecrash.consolepipe.Console.ConsolePlayer;
import me.gamecrash.consolepipe.Console.ConsolePlayerCache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class ConsolePipe extends JavaPlugin {
    public ConsolePlayerCache cache;
    public ConsoleFilter filter;

    @Override
    public void onEnable() {
        reload();
        Logger root = (Logger) LogManager.getRootLogger();
        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, cmds -> {
            cmds.registrar().register(new ConsolePipeCommand().build());
        });
        filter = new ConsoleFilter(cache.getPlayers());
        filter.start();
        root.addFilter(filter);
    }

    @Override
    public void onDisable() {
        Logger root = (Logger) LogManager.getRootLogger();
        filter.stop();
        if (cache != null) {
            cache.clear();
        }
    }

    public void reload() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        reloadConfig();
        if (cache != null) {
            cache.clear();
        } else { cache = new ConsolePlayerCache(); }
    }

    public static ConsolePipe getPlugin() {
        return (ConsolePipe) Bukkit.getPluginManager().getPlugin("ConsolePipe");
    }
    public void setFilterPlayers(ArrayList<ConsolePlayer> players) {
        if (filter != null) {
            filter.setPlayers(players);
        } else {
            filter = new ConsoleFilter(players);
        }
    }
    public ConsolePlayerCache getCache() { return cache; }
}
