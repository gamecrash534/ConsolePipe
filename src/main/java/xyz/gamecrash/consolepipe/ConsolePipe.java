package xyz.gamecrash.consolepipe;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import xyz.gamecrash.consolepipe.Commands.ConsolePipeCommand;
import xyz.gamecrash.consolepipe.Console.ConsolePlayerManager;
import xyz.gamecrash.consolepipe.Listeners.PlayerLeaveHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;

public final class ConsolePipe extends JavaPlugin {
    private ConsolePlayerManager manager;

    @Override
    public void onEnable() {
        Logger root = (Logger) LogManager.getRootLogger();
        manager = new ConsolePlayerManager();
        reload();

        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, cmds -> {
            cmds.registrar().register(new ConsolePipeCommand().build(), "consolepipe", Collections.singletonList("cp"));
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
