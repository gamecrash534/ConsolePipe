package xyz.gamecrash.consolepipe.logs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LogReader {
    private final Log log;

    public LogReader(Log log) { this.log = log; }

    public List<String> getAllLines() throws IOException {
        return Files.readAllLines(Paths.get(log.getPath()));
    }

    public String readLine(int lineNumber) throws IOException {
        List<String> lines = getAllLines();
        return (lineNumber >= 0 && lineNumber < lines.size()) ? lines.get(lineNumber) : null;
    }

    public List<String> search(String regex) throws IOException {
        Pattern pattern = Pattern.compile(regex);
        return getAllLines().stream()
                .filter(line -> pattern.matcher(line).find())
                .collect(Collectors.toList());
    }

    public String getContent() throws IOException {
        return String.join("\n", getAllLines());
    }
}