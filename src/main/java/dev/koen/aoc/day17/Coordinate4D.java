package dev.koen.aoc.day17;

import io.vavr.Tuple2;
import io.vavr.Tuple3;
import io.vavr.collection.List;

record Coordinate4D(int x, int y, int z, int w) {

    public List<Coordinate4D> neighbours() {
        final var ys = List.of(y - 1, y, y + 1);
        final var zs = List.of(z - 1, z, z + 1);
        final var ws = List.of(w - 1, w, w + 1);

        return List.of(x - 1, x, x + 1)
                .flatMap(x -> ys.map(y -> new Tuple2<>(x, y)))
                .flatMap(xy -> zs.map(z -> new Tuple3<>(xy._1, xy._2, z)))
                .flatMap(xyz -> ws.map(w -> new Coordinate4D(xyz._1, xyz._2, xyz._3, w)))
                .filterNot(this::equals);
    }
}
