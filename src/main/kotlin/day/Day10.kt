package day

import java.util.*

object Day10 : Day(10) {
    private val map = input.lines().map { it.toList().map { it.toString().toIntOrNull() ?: -1 } }

    private data class Point(val x: Int, val y: Int, val height: Int)

    private val heads = map.mapIndexed { y, row ->
        row.mapIndexed { x, value ->
            if (value == 0) Point(x, y, value) else null
        }
    }.flatten().filterNotNull()

    override fun part1(): Any {
        return heads.sumOf { trailHead ->
            var visited = mutableSetOf<Point>()
            val queue: Queue<Point> = LinkedList()
            queue += trailHead

            while (queue.isNotEmpty()) {
                val point = queue.poll()
                if (!visited.add(point)) continue
                queue.addAll(getNextPoints(point))
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

                queue.addAll(getNextPoints(point))
            }

            n
        }
    }

    private fun getNextPoints(point: Point): List<Point> {
        return listOfNotNull(
            point.getNeighborPoint(0, -1),
            point.getNeighborPoint(0, 1),
            point.getNeighborPoint(-1, 0),
            point.getNeighborPoint(1, 0),
        )
    }

    private fun Point.getNeighborPoint(yOffset: Int, xOffset: Int): Point? {
        return map.getOrNull(y + yOffset)?.getOrNull(x + xOffset)
            ?.takeIf { it == this.height + 1 }
            ?.let { Point(x + xOffset, y + yOffset, it) }
    }
}