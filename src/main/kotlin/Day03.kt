object Day03 : Day(3) {
    private fun String.calculate(): Int {
        val regex = """mul\((\d+),(\d+)\)""".toRegex()
        return regex.findAll(this).sumOf {
            it.groupValues.let { it[1].toInt() * it[2].toInt() }
        }
    }

    override fun part1(): Any = input.calculate()

    override fun part2(): Any {
        val excludeRegex = Regex("""don't\(\).*?(do\(\)|$)""", RegexOption.DOT_MATCHES_ALL)
        return excludeRegex.replace(input, "").calculate()
    }
}