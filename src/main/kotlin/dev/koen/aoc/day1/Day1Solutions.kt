package dev.koen.aoc.day1

import java.io.File

fun main() {
    val numbers = File("src/test/resources/input-one.txt")
        .readLines()
        .map(String::toInt)

    println(result(numbers.findPairWithSumOf(2020)?.toList()))
    println(result(numbers.findTripleWithSumOf(2020)?.toList()))
}

private fun result(values: List<Int>?): String {
    return values?.reduce { a, b -> a * b }
        ?.let { "Result: $it" }
        ?: "No combination found"
}

private fun List<Int>.findPairWithSumOf(sum: Int): Pair<Int, Int>? {
    return flatMapIndexed { firstIndex, first ->
        filterIndexed { secondIndex, second -> first + second == sum && firstIndex != secondIndex }
            .map { second -> Pair(first, second) }
    }.firstOrNull()
}


private fun List<Int>.findTripleWithSumOf(sum: Int): Triple<Int, Int, Int>? {
    return flatMapIndexed { firstIndex, first ->
        flatMapIndexed { secondIndex, second ->
            filterIndexed { thirdIndex, third ->
                first + second + third == sum && listOf(firstIndex, secondIndex, thirdIndex).distinct().size == 3
            }.map { third -> Triple(first, second, third) }
        }
    }.firstOrNull()
}