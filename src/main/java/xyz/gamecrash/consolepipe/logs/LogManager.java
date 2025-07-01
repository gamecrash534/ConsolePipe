package xyz.gamecrash.consolepipe.logs;

import javax.annotation.Nullable;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class LogManager {
    private final List<Log> logs = new ArrayList<>();
    private final Map<UUID, Log> selectedLogs = new HashMap<>();

    public LogManager() {
        logs.addAll(getAllServerLogs());
    }

    public void setPlayerLog(UUID playerId, Log log) {
        selectedLogs.put(playerId, log);
    }
    public @Nullable Log getPlayerLog(UUID playerId) {
        return selectedLogs.get(playerId);
    }

    private List<Log> getAllServerLogs() {
        List<Log> logs = new ArrayList<>();

        logs.add(getLatestLog());
        addIfExists(logs, "debug", "logs/debug.log");

        logs.addAll(getLogFiles("logs"));
        logs.addAll(getCrashReports());

        return logs;
    }
    public @Nullable Log getLatestLog() {
        String latestLogPath = "logs/latest.log";
        if (new File(latestLogPath).exists()) {
            return new Log("latest.log", latestLogPath);
        }
        return null;
    }
    public @Nullable Log getLog(String logName) {
        return logs.stream()
            .filter(log -> log.getName().equalsIgnoreCase(logName))
            .findFirst()
            .orElse(null);
    }
    public @Nullable List<String> getLogNames() {
        List<Log> logs = getAllServerLogs();
        if (logs.isEmpty()) {
            return null;
        }
        return logs.stream()
            .map(Log::getName)
            .toList();
    }
    public boolean exists(String logName) {
        return logs.stream().anyMatch(log -> log.getName().equalsIgnoreCase(logName));
    }
    public void refreshLogs() {
        logs.clear();
        logs.addAll(getAllServerLogs());
    }

    private void addIfExists(List<Log> logs, String name, String path) {
        if (new File(path).exists()) {
            logs.add(new Log(name, path));
        }
    }
    private @Nullable List<Log> getLogFiles(String directory) {
        try (Stream<Path> paths = Files.walk(Paths.get(directory))) {
            return paths.filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".log") || path.toString().endsWith(".log.gz"))
                .map(path -> new Log(path.getFileName().toString(), path.toString()))
                .toList();
        } catch (Exception e) {
            return null;
        }
    }
    private List<Log> getCrashReports() { return getLogFiles("crash-reports"); }
}