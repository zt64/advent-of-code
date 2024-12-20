package day

import util.*
import java.util.*

object Day18 : Day(18) {
    private val points = input.lines().map {
        it.split(",").let { Point(it[0].toInt(), it[1].toInt()) }
    }

    private const val OFFSET = 1024

    override fun part1(): Any {
        return shortestPath(points.take(OFFSET))!!
    }

    override fun part2(): Any {
        for (i in OFFSET until points.size) {
            val currentPoints = points.take(i)
            if (shortestPath(currentPoints) == null) return currentPoints.last()
        }

        error("No solution found")
    }

    // returns the shortest path, or null if it doesn't exist
    private fun shortestPath(points: List<Point>): Int? {
        val width = points.maxOf { it.x } + 1
        val height = points.maxOf { it.y } + 1

        val grid = Grid(height, width) { '.' }.apply {
            points.forEach { (x, y) -> this[y][x] = '#' }
        }

        val seen = mutableSetOf<Point>()
        val q = ArrayDeque<Pair<Point, Int>>()
        q.add(Point(0, 0) to 0)

        while (q.isNotEmpty()) {
            val (p, steps) = q.removeFirst()

            if (p == Point(width - 1, height - 1)) return steps

            Directions.CARDINALS.forEach { dir ->
                val np = p + dir

                if (grid.getOrNull(np) != '.' || !seen.add(np)) return@forEach

                q.add(np to steps + 1)
            }
        }

        return null
    }
}