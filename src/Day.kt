abstract class Day(number: Int) {
    protected val input = readInput("Day${number.toString().padStart(2, '0')}")

    protected abstract fun part1(): Any
    protected abstract fun part2(): Any

    open fun main() {
        println("Part 1: ${part1()}")
        println("Part 2: ${part2()}")
    }
}