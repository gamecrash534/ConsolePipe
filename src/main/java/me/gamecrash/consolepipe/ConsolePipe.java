package me.gamecrash.consolepipe;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import me.gamecrash.consolepipe.Commands.ConsolePipeCommand;
import me.gamecrash.consolepipe.Console.ConsoleFilter;
import me.gamecrash.consolepipe.Console.ConsolePlayerCache;
import me.gamecrash.consolepipe.Events.PlayerLeaveHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ConsolePipe extends JavaPlugin {
    private ConsolePlayerCache cache;
    private ConsoleFilter filter;

    @Override
    public void onEnable() {
        Logger root = (Logger) LogManager.getRootLogger();
        filter = new ConsoleFilter();
        reload();

        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, cmds -> {
            cmds.registrar().register(new ConsolePipeCommand().build());
        });
        Bukkit.getPluginManager().registerEvents(new PlayerLeaveHandler(), this);

        filter.start();
        root.addFilter(filter);
    }

    @Override
    public void onDisable() {
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
        cache.setUpdateCallback(filter::updatePlayers);
    }

    public static ConsolePipe getPlugin() {
        return (ConsolePipe) Bukkit.getPluginManager().getPlugin("ConsolePipe");
    }

    public ConsolePlayerCache getCache() { return cache; }
    public ConsoleFilter getFilter() { return filter; }
}
