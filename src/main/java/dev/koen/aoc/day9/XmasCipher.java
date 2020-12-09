package dev.koen.aoc.day9;

import java.util.List;
import java.util.NoSuchElementException;
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

    static Function<List<Long>, Optional<long[]>> seqSum(long search) {
        return numbers -> {
            var index = 0;
            var sum = 0;

            do {
                sum += numbers.get(index);
                index++;
            } while (sum < search && index < numbers.size());

            return sum == search
                    ? Optional.ofNullable(numbers.subList(0, index).stream().mapToLong(Long::longValue).toArray())
                    : Optional.empty();
        };
    }

    Long failure() {
        return failures().findAny().orElseThrow(NoSuchElementException::new);
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
                .flatMap(l -> preamble.stream().filter(other -> other + l == sum))
                .count() == 0;
    }

    long[] sequenceSummingTo(long sum) {
        return IntStream.range(0, numbers.size())
                .mapToObj(index -> numbers.subList(index, numbers.size()))
                .map(seqSum(sum))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findAny()
                .orElseThrow(IllegalStateException::new);
    }
}
