package dev.koen.aoc.day9;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

class XmasCipher {

    private final List<Long> numbers;
    private final int preambleSize;

    XmasCipher(List<Long> numbers, int preambleSize) {
        this.numbers = numbers;
        this.preambleSize = preambleSize;
    }

    static Function<List<Long>, Optional<long[]>> findSequentialSum(long sum) {
        return subListOfNumbers -> IntStream.range(0, subListOfNumbers.size())
                .mapToObj(index -> subListOfNumbers.subList(0, index + 1).stream().mapToLong(Long::longValue).toArray())
                .takeWhile(sequence -> LongStream.of(sequence).sum() <= sum)
                .filter(sequence -> LongStream.of(sequence).sum() == sum)
                .findAny();
    }

    LongStream failures() {
        return IntStream.range(preambleSize, numbers.size())
                .filter(this::isFailure)
                .mapToLong(numbers::get);
    }

    boolean isFailure(int index) {
        final var sum = numbers.get(index);
        final var preamble = numbers.subList(index - preambleSize, index);
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
