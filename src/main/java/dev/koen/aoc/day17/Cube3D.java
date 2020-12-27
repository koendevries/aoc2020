package dev.koen.aoc.day17;

import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Map;

import java.util.function.BiFunction;
import java.util.function.Function;

record Cube3D(Map<Coordinate3D, Boolean> coordinates) {

    public Cube3D next(int times) {
        return times == 1
                ? next()
                : next().next(times - 1);
    }

    public Cube3D next() {
        final var outer = outer();
        final var wrappedAndTransformed = coordinates.merge(outer).map(transform());

        final var next = wrappedAndTransformed.containsAll(outer)
                ? wrappedAndTransformed.removeAll(outer.keySet())
                : wrappedAndTransformed;

        return new Cube3D(next);
    }

    private BiFunction<Coordinate3D, Boolean, Tuple2<Coordinate3D, Boolean>> transform() {
        return (coordinate3D, isActive) -> {
            final var activeNeighbours = coordinate3D.neighbours()
                    .map(c -> coordinates.get(c).getOrElse(false))
                    .filter(b -> b)
                    .count(Boolean::booleanValue);

            final var nextActive = activeNeighbours == 3 || activeNeighbours == 2 && isActive;

            return new Tuple2<>(coordinate3D, nextActive);
        };
    }

    public Map<Coordinate3D, Boolean> outer() {
        final var min = coordinates.keySet().minBy((a, b) -> a.x() <= b.x() && a.y() <= b.y() && a.z() <= b.z() ? -1 : 1).get();
        final var max = coordinates.keySet().maxBy((a, b) -> a.x() <= b.x() && a.y() <= b.y() && a.z() <= b.z() ? -1 : 1).get();


        final var frontAndBack = List.range(min.x() - 1, max.x() + 2)
                .flatMap(x -> List.range(min.y() - 1, max.y() + 2)
                        .flatMap(y -> List.of(
                                new Coordinate3D(x, y, min.z() - 1),
                                new Coordinate3D(x, y, max.z() + 1))
                        )
                );

        final var leftAndRight = List.range(min.z() - 1, max.z() + 2)
                .flatMap(z -> List.range(min.y() - 1, max.y() + 2)
                        .flatMap(y -> List.of(
                                new Coordinate3D(min.x() - 1, y, z),
                                new Coordinate3D(max.x() + 1, y, z))
                        )
                );


        final var topAndBottom = List.range(min.x() - 1, max.x() + 2)
                .flatMap(x -> List.range(min.z() - 1, max.z() + 2)
                        .flatMap(z -> List.of(
                                new Coordinate3D(x, min.y() - 1, z),
                                new Coordinate3D(x, max.y() + 1, z))
                        )
                );

        return frontAndBack.appendAll(leftAndRight).appendAll(topAndBottom)
                .distinct()
                .toMap(Function.identity(), v -> false);

    }

    public long active() {
        return coordinates.values().count(Boolean::booleanValue);
    }

}
