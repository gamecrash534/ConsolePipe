package xyz.gamecrash.consolepipe.logs;

import java.io.File;
import java.util.*;

public class Log {
    private final String name;
    private final String path;

    public Log(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() { return name; }
    public String getPath() { return path; }

    public boolean exists() { return new File(path).exists(); }
    public long getFileSize() {
        File file = new File(path);
        return file.exists() ? file.length() : 0;
    }
    public Date getLastModified() {
        File file = new File(path);
        return file.exists() ? new Date(file.lastModified()) : null;
    }
}