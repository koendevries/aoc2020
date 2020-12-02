package dev.koen.aoc.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class FileReader {

    private FileReader() {
        throw new IllegalStateException("Unable to create instance of FileReader");
    }

    public static List<String> readLines(Path p) {
        try (final var lines = Files.lines(p)) {
            return lines.collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid input file in path " + p.toString());
        }
    }
}
