package day

import util.halve
import util.toDigits

class Day02(input: String) : Day(input) {
    private val idRanges = input
        .replace("\n", "")
        .split(",")
        .map {
            it.split("-").map { it.toLong() }
        }
        .map { it[0]..it[1] }

    override fun part1(): Any {
        return idRanges.sumOf { idRange ->
            idRange.sumOf { num ->
                val numString = num.toString()

                val (first, second) = numString.halve()

                if (first == second) num else 0
            }
        }
    }

    override fun part2(): Any {
        return idRanges.sumOf { range ->
            range.sumOf { num ->
                val digits = num.toDigits().reversed()
                val len = digits.size

                val invalid = (1..len / 2).any { size ->
                    len % size == 0 && digits.chunked(size).let { chunks ->
                        chunks.all { it == chunks[0] }
                    }
                }

                if (invalid) num else 0
            }
        }
    }
}