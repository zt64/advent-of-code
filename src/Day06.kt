fun main() {
    val input = readInput("Day06")

    fun part1(): Int {
        val distinctItems = 4

        val window = input.windowed(distinctItems).first { window ->
            window.toList().distinct().size == distinctItems
        }

        return input.substringBefore(window).length + distinctItems
    }

    fun part2(): Int {
        val distinctItems = 14

        val window = input.windowed(distinctItems).first { window ->
            window.toList().distinct().size == distinctItems
        }

        return input.substringBefore(window).length + distinctItems
    }

    println(part1())
    println(part2())
}