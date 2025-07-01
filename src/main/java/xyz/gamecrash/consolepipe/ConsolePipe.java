package xyz.gamecrash.consolepipe;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import xyz.gamecrash.consolepipe.commands.ConsolePipeCommand;
import xyz.gamecrash.consolepipe.console.ConsolePlayerManager;
import xyz.gamecrash.consolepipe.listeners.PlayerLeaveHandler;
import xyz.gamecrash.consolepipe.logs.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;

public final class ConsolePipe extends JavaPlugin {
    private static ConsolePipe instance;
    private ConsolePlayerManager consolePlayerManager;
    private LogManager logManager;

    @Override
    public void onEnable() {
        instance = this;

        Logger root = (Logger) org.apache.logging.log4j.LogManager.getRootLogger();
        consolePlayerManager = new ConsolePlayerManager();
        logManager = new LogManager();
        reload();

        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, cmds -> {
            cmds.registrar().register(new ConsolePipeCommand().build(), "consolepipe", Collections.singletonList("cp"));
        });
        Bukkit.getPluginManager().registerEvents(new PlayerLeaveHandler(), this);

        consolePlayerManager.start();
        root.addFilter(consolePlayerManager);
    }

    @Override
    public void onDisable() {
        consolePlayerManager.stop();
        if (consolePlayerManager != null) {
            consolePlayerManager.clear();
        }
    }

    public void reload() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        reloadConfig();
        if (consolePlayerManager != null) {
            consolePlayerManager.clear();
        } else { consolePlayerManager = new ConsolePlayerManager(); }
    }

    public static ConsolePipe getPlugin() { return instance; }
    public ConsolePlayerManager getConsolePlayerManager() { return consolePlayerManager; }
    public LogManager getLogManager() { return logManager; }
}
