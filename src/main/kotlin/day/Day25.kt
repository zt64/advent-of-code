package day

object Day25 : Day(25) {
    override fun part1(): Any {
        val (locks, keys) = input.split("\n\n").partition { it[0] == '#' }

        return locks.sumOf { lock ->
            keys.count { key ->
                lock.indices.none { i -> key[i] == '#' && key[i] == lock[i] }
            }
        }
    }

    override fun part2() = "No part 2 today"
}