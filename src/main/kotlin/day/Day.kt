package day

sealed class Day(protected val input: String) {
    val number: Int = this::class.simpleName!!.removePrefix("Day").toInt()

    abstract fun part1(): Any
    abstract fun part2(): Any
}