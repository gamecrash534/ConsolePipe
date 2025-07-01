package xyz.gamecrash.consolepipe.logs;

import it.unimi.dsi.fastutil.Pair;
import xyz.gamecrash.consolepipe.ConsolePipe;
import xyz.gamecrash.consolepipe.config.ConfigEntries;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LogUploader {
    private static final ConsolePipe plugin = ConsolePipe.getPlugin();

public static Pair<Boolean, String> uploadLog(Log log) {
    try {
        String logContent = new LogReader(log).getContent();
        URL url = new URL(plugin.getConfig().getString(ConfigEntries.UPLOAD_URL));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "text/log");

        try (OutputStream os = conn.getOutputStream()) {
            os.write(logContent.getBytes());
        }

        String responseBody;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            responseBody = in.lines().reduce("", (acc, line) -> acc + line);
        }

        int responseCode = conn.getResponseCode();
        conn.disconnect();

        if (responseCode == HttpURLConnection.HTTP_ACCEPTED || responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
            return Pair.of(true, responseBody);
        } else {
            return Pair.of(false, "Failed to upload log: " + conn.getResponseMessage());
        }
    } catch (Exception e) {
        return Pair.of(false, "Failed to read log content: " + e.getMessage());
    }
}
}
