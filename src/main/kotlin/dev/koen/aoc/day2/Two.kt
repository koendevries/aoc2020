package dev.koen.aoc.day2

import java.io.File

fun main() {
    File("src/test/resources/input-two.txt")
        .readLines()
        .map(String::toPolicy2)
        .filter(Policy2::isValid)
        .count()
        .let { amountOfValidPasswords -> println(amountOfValidPasswords) }
}

private fun String.toPolicy2(): Policy2 {
    val parts = this.split(" ")

    val indices = parts[0].toIndices()
    val c = parts[1].toCharArray()[0]
    val password = parts[2]

    return Policy2(indices, c, password)
}

private fun String.toIndices(): Pair<Int, Int> {
    val numbers = this.split("-")

    val start = numbers[0].toInt()
    val end = numbers[1].toInt()

    return Pair(start - 1, end - 1)
}

private data class Policy2(val pair: Pair<Int, Int>, val char: Char, val password: String) {
    fun isValid(): Boolean {
        return (password[pair.first] == char) xor (password[pair.second] == char)
    }
}