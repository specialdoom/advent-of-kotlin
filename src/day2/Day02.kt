package day2

import readInput
import kotlin.math.absoluteValue

fun main() {
    val input = readInput("day2/Day02")

    // BEFORE LIVE APPROACH (part 1)

    fun convertLines(input: List<String>): List<List<Long>> {
        return input.map{ line ->
            line.split(" ").map{it.toLong()}
        }
    }

    fun isSafe(element: Long): Boolean {
        return element in 1L..3L || element in -3L..-1L
    }

    fun computeDifferences(list: List<Long>): List<Long> {
        val differences: MutableList<Long> = mutableListOf()
        var index = 0
        while(index < list.size - 1) {
            val currentItem = list[index]
            val nextItem = list[index + 1]
            val difference = currentItem - nextItem

            differences.add(difference)

            index++
        }

        return differences
    }

    fun canBeTolerated(differences: List<Long>): Boolean {
        val levels = differences.count{diff -> !isSafe(diff)}

        return levels < 2;
    }

    fun computeSafety(list: List<Long>): Boolean {
        val reportDifferences = computeDifferences(list)

        return reportDifferences.all{isSafe(it) && it > 0} || reportDifferences.all{isSafe(it) && it < 0}
    }

     // INFO: Incomplete
    fun computeSafetyWithToleration(differences: List<List<Long>>): Int {
        return differences.map{diff -> canBeTolerated(diff)}.count{el -> el}
    }

    fun beforeLiveApproach(input: List<String>) {
        val reports = convertLines(input)
        val reportDifferences = reports.map{report -> computeDifferences(report)}
        val number = computeSafetyWithToleration(reportDifferences)
        val result = reports.map{report -> computeSafety(report)}.count{report -> report}
        println("----- BEFORE LIVE -----")
        println("Computed reports: ${reports.count()}")
        println("Safe reports: $result")
        println("Toleratable reports: $number")
        println("----- BEFORE LIVE -----")
    }

    beforeLiveApproach(input)

    // AFTER LIVE APPROACH

    fun isLineSafe(numbers: List<Int>): Boolean {
        var safe = true
        var isUp = true
        var isDown = true
        for (i in 0..numbers.lastIndex - 1) {
            val a = numbers[i]
            val b = numbers[i+1]
            safe = safe && ((a-b).absoluteValue <= 3)
            when {
                a < b -> isDown = false
                b < a -> isUp = false
                else -> {
                    isUp = false
                    isDown = false
                }
            }
        }

        return safe && (isUp || isDown);
    }

    // BASICALLY SAME AS MY INITIAL APPROACH BUT FUNCTIONAL
    fun isLineSafeFunctional(numbers: List<Int>): Boolean {
        val differences = numbers.zipWithNext{a, b -> a -b}
        return differences.all{it in -3..3} && (differences.all {it> 0} || differences.all{it < 0})
    }

    fun isLineSafeToleratedFunctional(input: List<List<Int>>): Int {
        return input.count{numbers ->
            numbers.indices.any{
                val skipped = numbers.toMutableList().apply{removeAt(it)}
                isLineSafeFunctional(skipped)
            }
        }
    }


    fun afterLiveApproach(input: List<String>) {
        val numbers = input.map{ line ->
            line.split(" ").map{it.toInt()}
        }

        val result = numbers.count(::isLineSafe);
        val resultFunctional = numbers.count(::isLineSafeFunctional);
        val resultToleratedFunctional = isLineSafeToleratedFunctional(numbers);

        println("----- BEFORE LIVE -----")
        println("Computed reports: ${numbers.count()}")
        println("Safe reports: $result")
        println("Safe reports (functional): $resultFunctional")
        println("Tolerated reports (functional): $resultToleratedFunctional")
        println("----- BEFORE LIVE -----")
    }


    afterLiveApproach(input)
}
