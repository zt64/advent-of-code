package day

import kotlin.math.sign

class Day01(input: String) : Day(input) {
    private val reg = "(.)(.*)".toRegex()
    private val turns: List<Int> = input.lines().map {
        reg.matchEntire(it)!!.destructured.let { (dir, clicks) ->
            if (dir == "R") clicks.toInt() else -clicks.toInt()
        }
    }

    companion object {
        const val DIAL_START = 50
        const val DIAL_MAX = 100
    }

    override fun part1(): Any {
        var dial = DIAL_START
        return turns.count { clicks ->
            dial = (dial - clicks).wrap(DIAL_MAX).first

            dial % 100 == 0
        }
    }

    override fun part2(): Any {
        var n = 0
        var dial = DIAL_START
        turns.forEach { clicks ->
            dial += clicks
            if (clicks.sign == -1) {
                while (dial < 0) {
                    dial += 100
                    n++
                }
            } else {
                while (dial > 99) {
                    dial -= 100
                    n++
                }
            }
        }
        return n
    }

    private fun Int.wrap(max: Int): Pair<Int, Boolean> {
        val mod = this % max
        val wrapped = mod < 0 || this >= max
        val value = if (mod < 0) mod + max else mod
        return value to (wrapped && value != max)
    }
}