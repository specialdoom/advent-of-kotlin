package day2

import readInput

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
        println("Computed reports: ${reports.count()}")
        println("Safe reports: $result")
        println("Toleratable reports: $number")
    }


    beforeLiveApproach(input)

}
