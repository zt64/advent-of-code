fun main() {
    val input = readInput("Day05")
    val regex = """move (\d{1,2}) from (\d{1,2}) to (\d{1,2})""".toRegex()

    fun part1(input: String): String {
        val sections = input.split("\n\n")

        val storage = sections[0].lines()
        val instructions = sections[1].lines()

        val columns = storage.last().trimEnd().last().digitToInt()
        val rows = storage.dropLast(1)

        val map = buildMap<Int, List<Char>>(capacity = columns) {
            rows.forEach { row ->
                row
                    .prependIndent(" ")
                    .chunked(4)
                    .map { it[2] }
                    .forEachIndexed { index, crate ->
                        val offsetIndex = index + 1

                        if (crate.isLetter()) {
                            if (containsKey(offsetIndex)) {
                                put(offsetIndex, get(offsetIndex)!! + crate)
                            } else {
                                put(offsetIndex, mutableListOf(crate))
                            }
                        } else {
                            put(offsetIndex, emptyList())
                        }
                    }
            }
        }.toMutableMap()

        instructions.forEach { instruction ->
            val groupValues = regex.matchEntire(instruction)!!.groupValues.drop(1)
            val amount = groupValues[0].toInt()
            val source = groupValues[1].toInt()
            val dest = groupValues[2].toInt()

            val crates = map[source]!!.take(amount)
            map[source] = map[source]!!.drop(amount)

            map[dest] = crates + map[dest]!!
        }

        println(map)

        return map.values.joinToString(separator = "") { it.firstOrNull()?.toString() ?: ""}
    }

    fun part2(input: String): Int {
        // forgor
        return 0
    }

    println(part1(input))
    println(part2(input))
}