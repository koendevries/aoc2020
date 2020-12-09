package dev.koen.aoc.day9;

import dev.koen.aoc.util.FileReader;
import dev.koen.aoc.util.TimeUtil;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Solution {

    public static void main(String[] args) {
        final var numbers = FileReader.readLines(Path.of("src/test/resources/input-nine.txt")).stream()
                .map(Long::parseUnsignedLong)
                .collect(Collectors.toList());

        final var cipher = new XmasCipher(numbers, 25);

        final var firstFailure = TimeUtil.print(cipher, c -> c.failures().findFirst().orElseThrow(IllegalStateException::new));

        TimeUtil.print(cipher, c -> {
            final var sequence = c.sequenceSummingTo(firstFailure);
            final var min = LongStream.of(sequence).min().orElseThrow(IllegalStateException::new);
            final var max = LongStream.of(sequence).max().orElseThrow(IllegalStateException::new);

            return min + max;
        });
    }
}

record XmasCipher(List<Long> numbers, int preamble) {

    static Function<List<Long>, Optional<long[]>> findSequentialSum(long sum) {
        return subListOfNumbers -> IntStream.range(0, subListOfNumbers.size())
                .mapToObj(index -> subListOfNumbers.subList(0, index + 1).stream().mapToLong(Long::longValue).toArray())
                .takeWhile(sequence -> LongStream.of(sequence).sum() <= sum)
                .filter(sequence -> LongStream.of(sequence).sum() == sum)
                .findAny();
    }

    LongStream failures() {
        return IntStream.range(preamble, numbers.size())
                .filter(this::isFailure)
                .mapToLong(numbers::get);
    }

    boolean isFailure(int index) {
        final var sum = numbers.get(index);
        final var preamble = numbers.subList(index - this.preamble, index);
        return preamble.stream()
                .flatMap(l -> preamble.stream().map(other -> new long[]{l, other}))
                .noneMatch(p -> p[0] + p[1] == sum);
    }

    long[] sequenceSummingTo(long sum) {
        return IntStream.range(0, numbers.size())
                .mapToObj(index -> numbers.subList(index, numbers.size()))
                .map(findSequentialSum(sum))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findAny()
                .orElseThrow(IllegalStateException::new);
    }
}