from math import prod

TREE = '#'


def find_trees(file, slope):
    width = len(file[0])
    height = len(file)

    row_numbers = filter(lambda row_number: slope[1] * row_number < height, range(height))
    coordinates = map(lambda row_number: [row_number * slope[0], row_number * slope[1]], row_numbers)
    characters = map(lambda coordinate: file[coordinate[1]][coordinate[0] % width], coordinates)

    return filter(lambda c: c == TREE, characters)


def read_file():
    return list(map(str, open("../../test/resources/input-three.txt").read().split()))


def slopes():
    return [[1, 1], [3, 1], [5, 1], [7, 1], [1, 2]]


if __name__ == "__main__":
    tree_map = read_file()
    trees_per_slope = map(lambda slope: len(list(find_trees(tree_map, slope))), slopes())
    print(prod(list(trees_per_slope)))
