package day

import util.Point2D

class Day09(input: String) : Day(input) {
    private val tiles = input.lines().map { line ->
        line.split(",").map { it.toInt() }.let { Point2D(it[0], it[1]) }
    }

    private val containedPoints = HashMap<Point2D, Boolean>()

    private fun checkPoint(x: Int, y: Int): Boolean {
        return containedPoints.getOrPut(Point2D(x, y)) {
            val px = x.toLong() + 1
            val py = y.toLong() + 1

            var inside = false
            val n = tiles.size

            for (i in 0 until n) {
                val j = if (i == 0) n - 1 else i - 1

                val x1 = tiles[i].x.toLong()
                val y1 = tiles[i].y.toLong()
                val x2 = tiles[j].x.toLong()
                val y2 = tiles[j].y.toLong()

                if ((y1 > py) != (y2 > py) &&
                    px < (x2 - x1) * (py - y1) / (y2 - y1) + x1
                ) {
                    inside = !inside
                }
            }
            inside
        }
    }

    override fun part1(): Any {
        return tiles.maxOf { start ->
            tiles.maxOf { end ->
                (end.x - start.x.toLong() + 1) * (end.y - start.y.toLong() + 1)
            }
        }
    }

    override fun part2(): Any {
        return tiles.maxOf { start ->
            tiles.maxOf { end ->
                val width = end.x - start.x.toLong() + 1
                val height = end.y - start.y.toLong() + 1
                var valid =  true

                for (x in start.x..end.x step 20) {
                    if (!checkPoint(x, start.y)) { valid = false; break }
                    if (start.y != end.y && !checkPoint(x, end.y)) { valid = false; break }
                }

                if (valid) {
                    for (y in (start.y + 1) until end.y step 20) {
                        if (!checkPoint(start.x, y)) { valid = false; break }
                        if (start.x != end.x && !checkPoint(end.x, y)) { valid = false; break }
                    }
                }

                if (valid) width * height else 0
            }
        }
    }
}