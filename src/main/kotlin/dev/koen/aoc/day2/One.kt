package dev.koen.aoc.day2

import java.io.File

fun main() {
    File("src/test/resources/input-two.txt")
        .readLines()
        .map(String::toPolicy1)
        .filter(Policy1::isValid)
        .count()
        .let { amountOfValidPasswords -> println(amountOfValidPasswords) }
}

private fun String.toPolicy1(): Policy1 {
    val parts = this.split(" ")

    val range = parts[0].toIntRange()
    val c = parts[1].toCharArray()[0]
    val password = parts[2]

    return Policy1(range, c, password)
}

private fun String.toIntRange(): IntRange {
    val numbers = this.split("-")

    val start = numbers[0].toInt()
    val end = numbers[1].toInt()

    return IntRange(start, end)
}

private data class Policy1(val range: IntRange, val char: Char, val password: String) {
    fun isValid(): Boolean {
        return password.toCharArray()
            .filter(char::equals)
            .count()
            .let { range.contains(it) }
    }
}