val priorities = (('a'..'z') + ('A'..'Z'))
    .mapIndexed { index, char -> char to index + 1 }
    .toMap()

fun main() {
    val input = readInput("Day03")

    fun part1(input: String): Int {
        val rucksacks = input.lines()

        val total = rucksacks.sumOf { rucksack ->
            val middle = rucksack.length / 2

            val compartment1 = rucksack.take(middle).toList()
            val compartment2 = rucksack.takeLast(middle).toList()

            priorities[compartment1.intersect(compartment2.toSet()).single()]!!
        }

        return total
    }

    fun part2(input: String): Int {
        val groups = input.lines().chunked(3)

        return groups.sumOf { group ->
            val elf1 = group[0].toSet()
            val elf2 = group[1].toSet()
            val elf3 = group[2].toSet()

            priorities[elf1.intersect(elf2).intersect(elf3).single()]!!
        }
    }

    println(part1(input))
    println(part2(input))
}