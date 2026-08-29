package dev.gamecrash.consolepipe.model;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public record LogFile(Path path, List<String> contents) {
    public Set<String> getMatchingLines(Pattern pattern) {
        Matcher m = pattern.matcher("");
        return contents.stream()
            .filter(s -> m.reset(s).matches())
            .collect(Collectors.toSet());
    }
}
