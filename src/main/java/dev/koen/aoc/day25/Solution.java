package dev.koen.aoc.day25;

import dev.koen.aoc.util.FileReader;

import java.nio.file.Path;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static dev.koen.aoc.common.Longs.multiply;
import static dev.koen.aoc.common.Longs.remaining;

public class Solution {

    public static void main(String[] args) {
        final var publicKeys = FileReader.readLines(Path.of("src/test/resources/input-twentyfive.txt"))
                .stream()
                .map(Long::parseUnsignedLong)
                .collect(Collectors.toList());

        final var cardPublicKey = publicKeys.get(0);
        final var doorPublicKey = publicKeys.get(1);

        final var cardLoopSize = findLoopSize(7).apply(cardPublicKey);
        System.out.println(encryptionKey(cardLoopSize).apply(doorPublicKey));

        final var doorLoopSize = findLoopSize(7).apply(doorPublicKey);
        System.out.println(encryptionKey(doorLoopSize).apply(cardPublicKey));
    }

    private static Function<Long, Long> encryptionKey(long loopSize) {
        return subjectNumber -> LongStream.range(0L, Long.MAX_VALUE)
                .limit(loopSize)
                .reduce(1, (acc, next) -> transform(subjectNumber).apply(acc));
    }

    private static Function<Long, Long> findLoopSize(long subjectNumber) {
        return publicKey -> {
            var currentValue = 1L;
            long loopSize = 0;

            do {
                currentValue = transform(subjectNumber).apply(currentValue);
                loopSize++;
            } while (currentValue != publicKey);

            return loopSize;
        };
    }

    private static Function<Long, Long> transform(long subjectNumber) {
        return number -> multiply(subjectNumber)
                .andThen(remaining(20201227))
                .apply(number);
    }
}

