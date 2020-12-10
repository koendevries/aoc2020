package dev.koen.aoc.day10;

import dev.koen.aoc.util.FileReader;
import dev.koen.aoc.util.TimeUtil;

import java.nio.file.Path;
import java.util.stream.IntStream;

public class Solution {

    public static void main(String[] args) {
        final var intStream = FileReader.readLines(Path.of("src/test/resources/input-ten.txt"))
                .stream()
                .mapToInt(Integer::parseUnsignedInt);

        final var joltages = new Joltages(intStream);

        TimeUtil.printWithMillis(joltages, j -> j.differences(1).count() * joltages.differences(3).count());
        TimeUtil.printWithNano(joltages, Joltages::combinations);
    }
}

class Joltages {

    private final int[] joltages;
    private final int deviceJoltage;


    Joltages(IntStream joltages) {
        this.joltages = joltages.sorted().toArray();
        deviceJoltage = this.joltages[this.joltages.length - 1] + 3;
    }

    IntStream differences() {
        final var firstDifference = IntStream.of(joltages[0]);
        final var differences = IntStream.range(0, joltages.length - 1)
                .map(index -> joltages[index + 1] - joltages[index]);

        final var firstDifferences = IntStream.concat(firstDifference, differences);
        final var lastDifference = IntStream.of(deviceJoltage - joltages[joltages.length - 1]);

        return IntStream.concat(firstDifferences, lastDifference);
    }

    IntStream differences(int size) {
        return differences().filter(d -> d == size);
    }

    long combinations() {
        long[] combinations = new long[joltages.length];
        combinations[0] = 1;
        for (int i = 1; i < joltages.length; i++) {
            combinations[i] += combinations[i - 1];

            if (i >= 3 && joltages[i] - joltages[i - 3] <= 3) {
                combinations[i] += combinations[i - 3];
                combinations[i] += combinations[i - 2];
            } else if (i >= 2 && joltages[i] - joltages[i - 2] <= 3) {
                combinations[i] += combinations[i - 2];
            }

            if (i == 2 && joltages[i] <= 3) {
                combinations[i] += 2;
            } else if (i == 1 && joltages[i] <= 3) {
                combinations[i] += 1;
            }

        }
        return combinations[combinations.length - 1];
    }
}
