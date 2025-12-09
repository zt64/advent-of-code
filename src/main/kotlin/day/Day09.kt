package day

import util.Point2D

class Day09(input: String) : Day(input) {
    private val tiles = input.lines().map { line ->
        line.split(",").map { it.toInt() }.let { Point2D(it[0], it[1]) }
    }

    private fun checkPoint(point: Point2D): Boolean {
        val x = tiles.map { it.x.toLong()}
        val y = tiles.map { it.y.toLong() }
        var c = 0L
        val vertCount = tiles.size
        val px = point.x.toLong() + 1
        val py = point.y.toLong() + 1
        for (i in 0 until vertCount) {
            val j = (i - 1 + vertCount) % vertCount

            val x1 = x[i]
            val y1 = y[i]
            val x2 = x[j]
            val y2 = y[j]

            if ((y1 > py != (y2 > py)) && px < ((x2 - x1) * (py - y1) / (y2 - y1)) + x1) {
                c++
            }
        }
        return c % 2 == 1L
    }

    override fun part1(): Any {
        return tiles.maxOf { start ->
            tiles.maxOf { end ->
                val width = end.x - start.x.toLong() + 1
                val height = end.y - start.y.toLong() + 1

                width * height
            }
        }
    }

    override fun part2(): Any {
        val filtered = tiles.filter { checkPoint(it) }

        return filtered.maxOf { start ->
            filtered.maxOf { end ->
                val width = end.x - start.x.toLong() + 1
                val height = end.y - start.y.toLong() + 1

                width * height
            }
        }
    }
}