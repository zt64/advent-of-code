package day

object Day05 : Day(5) {
    private val sections = input.split("\n\n")
    private val rules = sections[0].lines().map { it.split("|").let { it[0].toInt() to it[1].toInt() } }
    private val pageNumbers = sections[1].lines().map { it.split(",").map { it.toInt() } }

    override fun part1(): Any {
        return pageNumbers.filter { line ->
            val rules = rules.filter { pair -> pair.second in line }

            line.all { num ->
                rules.all { pair -> line.satisfies(pair) }
            }
        }.sumOf { it[it.size / 2] }
    }

    override fun part2(): Any {
        return pageNumbers.filter { line ->
            val rules = rules.filter { pair -> pair.second in line }

            line.any { num ->
                !rules.all { pair -> line.satisfies(pair) }
            }
        }.sumOf { line ->
            line.sortedWith { p1, p2 -> p1.checkAgainst(p2, rules) }[line.size / 2]
        }
    }

    private fun List<Int>.satisfies(rule: Pair<Int, Int>): Boolean = indexOf(rule.first) < indexOf(rule.second)

    private fun Int.checkAgainst(other: Int, rules: List<Pair<Int, Int>>): Int {
        val rule = rules.first { this in it.toList() && other in it.toList() }
        return if (listOf(this, other).satisfies(rule)) -1 else 1
    }
}