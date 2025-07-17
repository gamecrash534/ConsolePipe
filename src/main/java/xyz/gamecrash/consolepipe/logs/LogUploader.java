package xyz.gamecrash.consolepipe.logs;

import it.unimi.dsi.fastutil.Pair;
import xyz.gamecrash.consolepipe.ConsolePipe;
import xyz.gamecrash.consolepipe.config.ConfigEntries;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class LogUploader {
    private static final ConsolePipe plugin = ConsolePipe.getPlugin();

    public static Pair<Boolean, String> uploadLog(Log log) {
        try {
            String logContent = new LogReader(log).getContent();
            return upload(logContent);
        } catch (Exception e) {
            return Pair.of(false, "Failed to read log content: " + e.getMessage());
        }
    }
    public static Pair<Boolean, String> uploadLog(Log log, int startLine, int endLine) {
        try {
            String[] lines = new LogReader(log).getContent().split("\n");
            endLine = Math.min(Math.max(startLine, endLine), lines.length);
            StringBuilder sb = new StringBuilder();
            for (int i = startLine; i < endLine; i++) {
                sb.append(lines[i]).append("\n");
            }
            return upload(sb.toString());
        } catch (Exception e) {
            return Pair.of(false, "Failed to read log content: " + e.getMessage());
        }
    }

    private static Pair<Boolean, String> upload(String logContent) throws Exception {
        URI uri = new URI(plugin.getConfig().getString(ConfigEntries.UPLOAD_URL, "https://api.pastes.dev/post"));
        URL url = uri.toURL();
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
    }
}
