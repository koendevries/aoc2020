package dev.koen.aoc.day17;

import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Map;

import java.util.function.BiFunction;
import java.util.function.Function;

record Cube4D(Map<Coordinate4D, Boolean> coordinates) {

    public Cube4D next(int times) {
        return times == 1
                ? next()
                : next().next(times - 1);
    }

    public Cube4D next() {
        final var outer = outer();
        final var wrappedAndTransformed = coordinates.merge(outer).map(transform());

        final var next = wrappedAndTransformed.containsAll(outer)
                ? wrappedAndTransformed.removeAll(outer.keySet())
                : wrappedAndTransformed;

        return new Cube4D(next);
    }

    private BiFunction<Coordinate4D, Boolean, Tuple2<Coordinate4D, Boolean>> transform() {
        return (coordinate4D, isActive) -> {
            final var activeNeighbours = coordinate4D.neighbours()
                    .map(c -> coordinates.get(c).getOrElse(false))
                    .filter(b -> b)
                    .count(Boolean::booleanValue);

            final var nextActive = activeNeighbours == 3 || activeNeighbours == 2 && isActive;

            return new Tuple2<>(coordinate4D, nextActive);
        };
    }

    public Map<Coordinate4D, Boolean> outer() {
        final var min = coordinates.keySet().minBy((a, b) -> a.x() <= b.x() && a.y() <= b.y() && a.z() <= b.z() && a.w() <= b.w() ? -1 : 1).get();
        final var max = coordinates.keySet().maxBy((a, b) -> a.x() <= b.x() && a.y() <= b.y() && a.z() <= b.z() && a.w() <= b.w() ? -1 : 1).get();


        final var frontAndBack = List.range(min.x() - 1, max.x() + 2)
                .flatMap(x -> List.range(min.w() - 1, max.w() + 2)
                        .flatMap(w -> List.range(min.y() - 1, max.y() + 2)
                                .flatMap(y -> List.of(
                                        new Coordinate4D(x, y, min.z() - 1, w),
                                        new Coordinate4D(x, y, max.z() + 1, w))
                                )
                        )
                );

        final var leftAndRight = List.range(min.z() - 1, max.z() + 2)
                .flatMap(z -> List.range(min.y() - 1, max.y() + 2)
                        .flatMap(y -> List.range(min.w() - 1, max.w() + 2)
                                .flatMap(w -> List.of(
                                        new Coordinate4D(min.x() - 1, y, z, w),
                                        new Coordinate4D(max.x() + 1, y, z, w))
                                )
                        )
                );


        final var topAndBottom = List.range(min.x() - 1, max.x() + 2)
                .flatMap(x -> List.range(min.z() - 1, max.z() + 2)
                        .flatMap(z -> List.range(min.w() - 1, max.w() + 2)
                                .flatMap(w -> List.of(
                                        new Coordinate4D(x, min.y() - 1, z, w),
                                        new Coordinate4D(x, max.y() + 1, z, w))
                                )
                        )
                );

        final var pastAndFuture = List.range(min.x() - 1, max.x() + 2)
                .flatMap(x -> List.range(min.z() - 1, max.z() + 2)
                        .flatMap(z -> List.range(min.y() - 1, max.y() + 2)
                                .flatMap(y -> List.of(
                                        new Coordinate4D(x, y, z, min.w() - 1),
                                        new Coordinate4D(x, y, z, max.w() + 1))
                                )
                        )
                );

        return frontAndBack.appendAll(leftAndRight).appendAll(topAndBottom).appendAll(pastAndFuture)
                .distinct()
                .toMap(Function.identity(), v -> false);

    }

    public long active() {
        return coordinates.values().count(Boolean::booleanValue);
    }

}
