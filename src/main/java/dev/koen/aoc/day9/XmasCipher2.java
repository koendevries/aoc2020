package dev.koen.aoc.day9;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

class XmasCipher2 {

    private final List<Long> numbers;
    private final int preambleSize;

    XmasCipher2(List<Long> numbers, int preambleSize) {
        this.numbers = numbers;
        this.preambleSize = preambleSize;
    }

    long failure() {
        for (int index = preambleSize; index < numbers.size(); index++) {
            if (isFailure(index)) {
                return numbers.get(index);
            }
        }
        throw new NoSuchElementException("Unable to find failure");
    }

    boolean isFailure(int index) {
        final var sum = numbers.get(index);
        final var preamble = numbers.subList(index - preambleSize, index);

        for (int i = 0; i < preambleSize - 1; i++) {
            final var first = preamble.get(i);

            for (int j = i + 1; j < preambleSize; j++) {
                final var second = preamble.get(j);
                if (first + second == sum) {
                    return false;
                }
            }
        }
        return true;
    }

    long[] contiguousSumToEqual(long search) {
        for (int i = 0; i < numbers.size(); i++) {
            var currentIndex = i;

            var sum = 0;
            do {
                sum += numbers.get(currentIndex);
                currentIndex++;
            } while (sum < search && currentIndex < numbers.size());

            if (sum == search) {
                return IntStream.range(i, currentIndex)
                        .mapToLong(numbers::get)
                        .toArray();
            }
        }
        throw new IllegalStateException("Unable to find contiguous sum value " + search);
    }
}
