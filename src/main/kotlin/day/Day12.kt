package day

import util.*
import java.util.*

object Day12 : Day(12) {
    private val grid = input.to2DArray<Char>()

    private fun calculatePrice(calculateEdgePrice: (Char, Int, Int) -> Int): Int {
        var total = 0
        val seen = mutableSetOf<Point>()

        grid.forEach2D { p, y, x ->
            val queue: Queue<Point> = LinkedList()
            val region = mutableSetOf<Point>()

            queue += y to x

            while (queue.isNotEmpty()) {
                val point = queue.poll()

                if (point in seen || !region.add(point)) continue

                Directions.CARDINALS.map { dir -> point + dir }
                    .filter { grid.getOrNull(it) == p }
                    .let { queue.addAll(it) }
            }

            total += region.sumOf { (y, x) -> calculateEdgePrice(p, y, x) } * region.size

            seen.addAll(region)
        }

        return total
    }

    override fun part1(): Any = calculatePrice { p, y, x ->
        Directions.CARDINALS.filter { dir -> grid.getOrNull(y, x, dir) != p }.size
    }

    override fun part2() = calculatePrice { p, y, x ->
        (Directions.CARDINALS + Directions.CARDINALS.first()).zipWithNext().filter { (dir1, dir2) ->
            val a = grid.getOrNull(y, x, dir1)
            val b = grid.getOrNull(y, x, dir2)
            val diag = grid.getOrNull(y + dir1.y + dir2.y, x + dir1.x + dir2.x)

            (a != p && b != p) || (a == p && b == p && diag != p)
        }.size
    }
}