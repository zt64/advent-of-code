fun main() {
    val input = readInput("Day01")

    println(part1(input))
    println(part2(input))
}

fun part1(input: String) = input
    .split("\n\n")
    .map { it.lines().filter(String::isNotBlank) }
    .map { it.map(String::toInt) }
    .map { it.sum() }
    .sorted()
    .max()

fun part2(input: String) = input
    .split("\n\n")
    .asSequence()
    .map { it.lines().filter(String::isNotBlank) }
    .map { it.map(String::toInt) }
    .map { it.sum() }
    .sortedDescending()
    .take(3)
    .sum()