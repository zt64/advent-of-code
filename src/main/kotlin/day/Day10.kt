package day

import util.*
import java.util.*

object Day10 : Day(10) {
    private val map = input.to2DArray { it.toString().toIntOrNull() ?: -1 }
    private val heads = map.mapIndexedNotNull2D { value, y, x ->
        if (value == 0) Point(x, y, value) else null
    }.flatten()

    private data class Point(val x: Int, val y: Int, val height: Int)

    override fun part1(): Any {
        return heads.sumOf { trailHead ->
            var visited = mutableSetOf<Point>()
            val queue: Queue<Point> = LinkedList()
            queue += trailHead

            while (queue.isNotEmpty()) {
                val point = queue.poll()
                if (!visited.add(point)) continue
                queue.addAll(point.getNeighborPoints())
            }

            visited.count { it.height == 9 }
        }
    }

    override fun part2(): Any {
        return heads.sumOf { trailHead ->
            var n = 0
            val queue: Queue<Point> = LinkedList()
            queue += trailHead

            while (queue.isNotEmpty()) {
                val point = queue.poll()
                if (point.height == 9) n++

                queue.addAll(point.getNeighborPoints())
            }

            n
        }
    }

    private fun Point.getNeighborPoints() = Directions.CARDINALS.mapNotNull { d ->
        map.getOrNull(y + d.y, x + d.x)
            ?.takeIf { it == height + 1 }
            ?.let { Point(x + d.x, y + d.y, it) }
    }
}