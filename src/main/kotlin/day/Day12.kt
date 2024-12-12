package day

import util.*
import util.Point
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

                Direction.entries.map { dir -> point + dir.offset }
                    .filter { grid.getOrNull(it.first, it.second) == p }
                    .let { queue.addAll(it) }
            }

            total += region.sumOf { (y, x) -> calculateEdgePrice(p, y, x) } * region.size

            seen.addAll(region)
        }

        return total
    }

    override fun part1(): Any = calculatePrice { p, y, x ->
        Direction.entries.filter { dir -> grid.getOrNull(y, x, dir) != p }.size
    }

    override fun part2() = calculatePrice { p, y, x ->
        (Direction.entries + Direction.entries.first()).zipWithNext().filter { (dir1, dir2) ->
            val a = grid.getOrNull(y, x, dir1)
            val b = grid.getOrNull(y, x, dir2)
            val diag = grid.getOrNull(y + dir1.offset.y + dir2.offset.y, x + dir1.offset.x + dir2.offset.x)

            (a != p && b != p) || (a == p && b == p && diag != p)
        }.size
    }
}