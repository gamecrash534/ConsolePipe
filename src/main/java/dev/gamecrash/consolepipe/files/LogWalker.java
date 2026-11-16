package dev.gamecrash.consolepipe.files;

import lombok.Getter;
import lombok.SneakyThrows;
import org.jspecify.annotations.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogWalker implements Iterable<Path> {
    private final Path directory;
    @Getter
    private Set<Path> result;

    public LogWalker(Path directory) {
        this.directory = directory;
        if (!Files.isDirectory(directory)) throw new IllegalArgumentException("given path is not a directory");
    }

    public LogWalker walk() throws IOException {
        try (Stream<Path> walker = Files.walk(directory, 1)) {
            result = walker.filter(Files::isRegularFile)
                .collect(Collectors.toSet());
        }

        return this;
    }

    @Override
    public @NonNull Iterator<Path> iterator() {
        return result.iterator();
    }
}
