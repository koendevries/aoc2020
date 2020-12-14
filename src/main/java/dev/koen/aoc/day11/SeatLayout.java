package dev.koen.aoc.day11;

import dev.koen.aoc.common.Coordinate;
import kotlin.ranges.IntRange;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class SeatLayout {

    private final Set<Seat> seats;
    private final IntRange width;
    private final IntRange height;

    SeatLayout(Set<Seat> seats) {
        this.seats = seats;
        var width = seats.stream()
                .mapToInt(s -> s.coordinate().x())
                .max().getAsInt();

        var height = seats.stream()
                .mapToInt(s -> s.coordinate().y())
                .max().getAsInt();

        this.width = new IntRange(0, width);
        this.height = new IntRange(0, height);
    }

    long occupied() {
        return seats.stream()
                .filter(s -> s.state() == State.OCCUPIED)
                .count();
    }

    Optional<SeatLayout> next() {
        final var next = seats.stream()
                .parallel()
                .map(this::nextSeat)
                .collect(Collectors.toSet());

        if (next.containsAll(seats)) {
            return Optional.empty();
        }
        return Optional.ofNullable(new SeatLayout(next));
    }

    private Seat nextSeat(Seat seat) {
        final var numberOfOccupiedInSight = numberOfOccupiedSeatsInSight(seat);

        if (seat.state() == State.EMPTY && numberOfOccupiedInSight == 0) {
            return new Seat(seat.coordinate(), State.OCCUPIED);
        } else if (seat.state() == State.OCCUPIED && numberOfOccupiedInSight >= 5) {
            return new Seat(seat.coordinate(), State.EMPTY);
        } else {
            return seat;
        }
    }

    long numberOfOccupiedSeatsInSight(Seat seat) {
        if (seat.state() == State.FLOOR) {
            return 0;
        }
        return Stream.of(
                seatInDirection(seat, new Coordinate(-1, -1)),
                seatInDirection(seat, new Coordinate(0, -1)),
                seatInDirection(seat, new Coordinate(1, -1)),
                seatInDirection(seat, new Coordinate(-1, 0)),
                seatInDirection(seat, new Coordinate(1, 0)),
                seatInDirection(seat, new Coordinate(-1, 1)),
                seatInDirection(seat, new Coordinate(0, 1)),
                seatInDirection(seat, new Coordinate(1, 1)))
                .filter(Optional::isPresent)
                .filter(s -> s.get().state() == State.OCCUPIED)
                .count();
    }

    Optional<Seat> seatInDirection(Seat start, Coordinate direction) {
        final var nextCoordinate = start.coordinate().transpose(direction);
        if (!inRange(nextCoordinate)) {
            return Optional.empty();
        }
        final var nextSeat = seat(nextCoordinate);
        if (nextSeat.state() == State.FLOOR) {
            return seatInDirection(nextSeat, direction);
        }

        return Optional.of(nextSeat);
    }

    Seat seat(Coordinate coordinate) {
        return seats.stream().filter(s -> coordinate.equals(s.coordinate())).findAny().orElseThrow();
    }

    boolean inRange(Coordinate coordinate) {
        return height.contains(coordinate.y()) && width.contains(coordinate.x());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeatLayout that = (SeatLayout) o;
        return seats.containsAll(((SeatLayout) o).seats) &&
                Objects.equals(width, that.width) &&
                Objects.equals(height, that.height);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seats, width, height);
    }
}
