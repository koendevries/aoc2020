package dev.koen.aoc.day18;

import dev.koen.aoc.util.FileReader;
import io.vavr.Function2;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Stream;

import java.nio.file.Path;
import java.util.function.Function;

public class Solution {

    private static final Function2<Long, Long, Long> multiply = (a, b) -> a * b;
    private static final Function2<Long, Long, Long> add = Long::sum;

    public static void main(String[] args) {
        final var result = Stream.ofAll(FileReader.readLines(Path.of("src/test/resources/input-eighteen.txt")))
                .map(solve())
                .reduceLeft(add);

        System.out.println(result);
    }

    private static Function<String, Long> solve() {
        return line -> {
            var cleanedUpLine = line;
            while (cleanedUpLine.contains(")")) {
                final var closingIndex = cleanedUpLine.indexOf(')');
                final var startingIndex = cleanedUpLine.substring(0, closingIndex).lastIndexOf('(');
                final var inner = cleanedUpLine.substring(startingIndex + 1, closingIndex);
                final var solved = solveSimpleLine(inner).toString();
                cleanedUpLine = cleanedUpLine.substring(0, startingIndex)
                        + solved
                        + cleanedUpLine.substring(closingIndex + 1);
            }

            return solveSimpleLine(cleanedUpLine);
        };
    }

    private static Long solveSimpleLine(String line) {
        final var numbersAndOperators = List.of(line.split("\\s+"))
                .map(String::strip)
                .filterNot(String::isBlank)
                .zipWithIndex()
                .partition(t -> t._2 % 2 == 0);

        final var numbers = numbersAndOperators._1
                .map(Tuple2::_1)
                .map(Long::parseUnsignedLong);

        final var functions = numbersAndOperators._2
                .map(t -> "*".equals(t._1) ? multiply : add)
                .zipWithIndex();

        var result = numbers.get(0);
        for (Tuple2<Function2<Long, Long, Long>, Integer> f : functions) {
            result = f._1.apply(result, numbers.get(f._2 + 1));
        }

        return result;
    }
}
