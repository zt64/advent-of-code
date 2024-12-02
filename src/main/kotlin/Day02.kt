import kotlin.math.absoluteValue

object Day02 : Day(2) {
    private val numbers = input.lines().map { it.split(" ").map { it.toInt() } }

    private fun isSafe(list: List<Int>): Boolean {
        if (list.sorted() != list && list.sortedDescending() != list) return false

        return list.zipWithNext().all { (a, b) -> (a - b).absoluteValue in 1..3 }
    }

    override fun part1(): Any {
        return numbers.count(::isSafe)
    }

    override fun part2(): Any {
        return numbers.count { list ->
            if (isSafe(list)) return@count true

            list.indices.any { i ->
                isSafe(list.toMutableList().apply { removeAt(i) })
            }
        }
    }
}