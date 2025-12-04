package day

import kotlin.math.abs

object Day03 : Day(3) {
    private val firstList = mutableListOf<Int>()
    private val secondList = mutableListOf<Int>()

    init {
        input.lines().forEach {
            val (left, right) = it.split("   ")

            firstList += left.toInt()
            secondList += right.toInt()
        }
    }

    override fun part1(): Any {
        val secondIterator = secondList.sorted().iterator()

        return firstList.sorted().sumOf { i ->
            abs(i - secondIterator.next())
        }
    }

    override fun part2(): Any {
        return firstList.sumOf { i ->
            secondList.count { j -> i == j } * i
        }
    }
}