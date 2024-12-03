package day3

import readInput

fun main() {
    val input = readInput("day3/Day03")

    fun parseSection(section: String): List<List<Long>> {
        var mul = ""
        val numbers: MutableList<List<Long>> = mutableListOf()
        var number = ""
        val partialNumbers: MutableList<Long> = mutableListOf()

        fun reset() {
            mul = ""
            number = ""
            with(partialNumbers) {
                clear()
            }
        }

        for (char in section) {
            if (char == 'm' && mul.isEmpty()) {
                mul += 'm'
                continue
            }

            if (char == 'u' && mul == "m") {
                mul += 'u'
                continue
            }

            if (char == 'l' && mul == "mu") {
                mul += 'l'
                continue
            }

            if (mul == "mul") {
                if (char == '(') {
                    mul += '('
                    continue
                } else {
                    reset()
                    continue
                }

            }

            if (char.isDigit() && (mul == "mul(" || mul == "mul(X,")) {
                number += char

                if(number.length > 3) {
                    reset()
                    continue
                }

                continue
            }

            if (char == ',' && mul == "mul(") {
                partialNumbers.add(number.toLong())
                mul = "mul(X,"
                number = ""
                continue
            }

            if(char != ',' && mul == "mul(") {
                reset()
                continue
            }

            if (mul == "mul(X,") {
                if (char == ')') {
                    partialNumbers.add(number.toLong())
                    numbers.add(partialNumbers.toList())
                    reset()
                    continue
                } else {
                    reset()
                    continue
                }
            }
        }

        return numbers.toList()
    }

    // EXAMPLE
    fun example() {
        val pairs = parseSection("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")
        val result = pairs.sumOf{ it.reduce { acc, i -> acc * i}}
        println("Example: $result")
    }

    // PART 1 with regex (something is odd)
    fun withRegex(input: List<String>): Long {
        val regex = "mul+\\([\\d{1,3}]+,[\\d{1,3}]+\\)".toRegex()
        val matchResult = regex.findAll(input.joinToString())
        val result = matchResult.map{it.value}
            .toList()
            .map{it.split("mul(", ",", ")").filter{splitIt -> splitIt.toLongOrNull() != null}.map{filteredIt -> filteredIt.toLong()}}
            .sumOf { it.reduce{acc, i -> acc * i} }

        return result
    }

    fun part1(input: List<String>) {
        val result = parseSection(input.joinToString()).sumOf{it.reduce{acc, i -> i * acc}}
        val result2 = input.sumOf{parseSection(it).sumOf{pair -> pair.reduce{acc, i -> i * acc}}}
        val withRegexResult = withRegex(input)
        println("Part 1 (without joining the string): $result2")
        println("Part 1: $result")
        println("Part 1 (with regex): $withRegexResult")
    }

    example()
    part1(input)
}