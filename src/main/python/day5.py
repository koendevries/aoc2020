def read_column(column):
    binary = ''.join(list(map(lambda c: "0" if c == 'L' else "1", list(column))))
    return int(binary, base=2)


def read_row(row):
    binary = ''.join(list(map(lambda c: "0" if c == 'F' else "1", list(row))))
    return int(binary, base=2)


def read_seat(line):
    row = read_row(line[0:7])
    column = read_column(line[7:])
    seat = [row, column]
    return seat


def read_file():
    return map(str, open("../../test/resources/input-five.txt").read().split())


def find_open_seat(taken_seat_ids, last_id):
    all_seats_ids = range(0, last_id)
    seats_with_neighbours = filter(lambda seat_id: has_neighbours(seat_id, taken_seat_ids), all_seats_ids)
    open_seats = filter(lambda seat_id: seat_id not in taken_seat_ids, seats_with_neighbours)
    return list(open_seats)[0]


def has_neighbours(seat_id, taken_seat_ids):
    left = seat_id - 1
    right = seat_id + 1
    return left in taken_seat_ids and right in taken_seat_ids


if __name__ == "__main__":
    lines = read_file()
    seats = map(read_seat, lines)
    ids = list(map(lambda s: s[0] * 8 + s[1], seats))
    last_id = max(ids)
    print(last_id)
    print(find_open_seat(ids, last_id))
