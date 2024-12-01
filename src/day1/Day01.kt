package day1

import readInput
import kotlin.math.abs

fun main() {
    fun splitColumns(input: List<String>): Pair<List<Long>, List<Long>> {
        return input.map { line ->
            val first = line.substringBefore(" ").toLong()
            val second = line.substringAfterLast(" ").toLong()
            first to second
        }.unzip()
    }

    fun part1(input: List<String>) {
        val (left, right) = splitColumns(input)

        val result = left.sorted().zip(right.sorted()).sumOf { (first, second) ->
            abs(first - second)
        }

        println("Part 1: $result")
    }

    fun part2(input: List<String>) {
        val (left, right) = splitColumns(input)

        val frequencies = right.groupingBy{it}.eachCount()

        val result = left.fold(0L){ acc, num ->
            acc + num * frequencies.getOrDefault(num, 0)
        }

        println("Part 2: $result")
    }


    val input = readInput("day1/Day01")

    part1(input)
    part2(input)
}
