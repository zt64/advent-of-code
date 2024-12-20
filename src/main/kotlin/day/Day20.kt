package day

import util.*

object Day20 : Day(20) {
    private val track = buildList {
        val grid = input.to2DArray<Char>()
        val start = grid['S']!!
        val end = grid['E']!!.also { grid[it] = '.' }

        val queue = ArrayDeque<Point>().apply { add(start) }
        val visited = mutableSetOf<Point>()

        while (queue.isNotEmpty()) {
            val p = queue.removeLast()
            add(p)

            if (p == end) break
            if (!visited.add(p)) continue

            queue.addAll(p.cardinalNeighbors.filter { grid[it] == '.' && it !in visited })
        }
    }

    private fun solve(cheatDuration: Int): Int {
        return (0 until track.lastIndex - 1).sumOf { i ->
            (i + 1 until track.size).count { j ->
                val dist = track[i].manhattanDistance(track[j])
                dist <= cheatDuration && j - i - dist >= 100
            }
        }
    }

    override fun part1() = solve(2)
    override fun part2() = solve(20)
}