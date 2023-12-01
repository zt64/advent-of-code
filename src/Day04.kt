fun main() {
    val input = readInput("Day04")

    fun part1(input: String): Int {
        var fullyContaining = 0

        input.lines().forEach { pair ->
            val (elf1, elf2) = pair.split(",")

            val (min1, max1) = elf1.split("-").map(String::toInt)
            val (min2, max2) = elf2.split("-").map(String::toInt)

            if (
                min1 <= min2 && max1 >= max2 ||
                min2 <= min1 && max2 >= max1
            ) fullyContaining++
        }

        return fullyContaining
    }

    fun part2(input: String): Int {
        var containing = 0

        input.lines().forEach { pair ->
            val (elf1, elf2) = pair.split(",")

            val (min1, max1) = elf1.split("-").map(String::toInt)
            val (min2, max2) = elf2.split("-").map(String::toInt)

            val assignment1 = min1..max1
            val assignment2 = min2..max2

            if (
                assignment1.first in assignment2 ||
                assignment1.last in assignment2 ||
                assignment2.first in assignment1 ||
                assignment2.last in assignment1
            ) containing++
        }

        return containing
    }

    println(part1(input))
    println(part2(input))
}