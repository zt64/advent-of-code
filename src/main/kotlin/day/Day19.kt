package day

object Day19 : Day(19) {
    private val cache = HashMap<String, Long>()
    private val towels = input.lines()[0].split(", ")
    private val arrangements = input.substringAfter("\n\n").lines().map(::countArrangements)

    private fun countArrangements(pattern: String): Long {
        if (pattern.isEmpty()) return 1
        return cache.getOrPut(pattern) {
            towels.sumOf { if (pattern.startsWith(it)) countArrangements(pattern.drop(it.length)) else 0 }
        }
    }

    override fun part1() = arrangements.count { it > 0 }
    override fun part2() = arrangements.sum()
}