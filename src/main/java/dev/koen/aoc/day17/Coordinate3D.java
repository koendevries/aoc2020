package dev.koen.aoc.day17;

import io.vavr.Tuple2;
import io.vavr.collection.List;

record Coordinate3D(int x, int y, int z) {

    public List<Coordinate3D> neighbours() {
        final var ys = List.of(y - 1, y, y + 1);
        final var zs = List.of(z - 1, z, z + 1);

        return List.of(x - 1, x, x + 1)
                .flatMap(x -> ys.map(y -> new Tuple2<>(x, y)))
                .flatMap(xy -> zs.map(z -> new Coordinate3D(xy._1, xy._2, z)))
                .filterNot(this::equals);
    }
}
