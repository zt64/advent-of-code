package day

import util.concat
import util.digitCount

class Day03(input: String) : Day(input) {
    private val banks = input.lines().map { it.map(Char::digitToInt) }

    override fun part1(): Any {
        return banks.sumOf { batteries ->
            findHighestCombo(batteries, 2)
        }
    }

    override fun part2(): Any {
        return banks.sumOf { batteries ->
            findHighestCombo(batteries, 12)
        }
    }

    private fun findHighestCombo(values: List<Int>, limit: Int): Int {
        var max = 0

        for (i in values.indices) {
            val a = values[i]

            var n = a
            for (remaining in 1 until limit) {
                println("Remaining $remaining")
                for (j in i + remaining until values.size) {
                    if (a.digitCount() >= limit) break

                    val total = concat(n, values[j])
                    if (total > max) {
                        max = total
                    }
                }
            }
        }

        return max
    }
}