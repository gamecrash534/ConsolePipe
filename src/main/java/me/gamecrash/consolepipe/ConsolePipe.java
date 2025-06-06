package me.gamecrash.consolepipe;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import me.gamecrash.consolepipe.Commands.ConsolePipeCommand;
import me.gamecrash.consolepipe.Console.ConsolePlayerManager;
import me.gamecrash.consolepipe.Listeners.PlayerLeaveHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ConsolePipe extends JavaPlugin {
    private ConsolePlayerManager manager;

    @Override
    public void onEnable() {
        Logger root = (Logger) LogManager.getRootLogger();
        manager = new ConsolePlayerManager();
        reload();

        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, cmds -> {
            cmds.registrar().register(new ConsolePipeCommand().build());
        });
        Bukkit.getPluginManager().registerEvents(new PlayerLeaveHandler(), this);

        manager.start();
        root.addFilter(manager);
    }

    @Override
    public void onDisable() {
        manager.stop();
        if (manager != null) {
            manager.clear();
        }
    }

    public void reload() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        reloadConfig();
        if (manager != null) {
            manager.clear();
        } else { manager = new ConsolePlayerManager(); }
    }

    public static ConsolePipe getPlugin() {
        return (ConsolePipe) Bukkit.getPluginManager().getPlugin("ConsolePipe");
    }
    public ConsolePlayerManager getManager() { return manager; }
}
