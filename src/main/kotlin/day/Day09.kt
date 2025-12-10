package day

import util.Point2D

class Day09(input: String) : Day(input) {
    private val tiles = input.lines().map { line ->
        line.split(",").map { it.toInt() }.let { Point2D(it[0], it[1]) }
    }

    private val containedPoints = HashMap<Point2D, Boolean>()

    init {
        tiles.windowed(2).forEach { (p1, p2) ->
            when {
                p1.x == p2.x -> {
                    val ys = p1.y.coerceAtMost(p2.y)..p1.y.coerceAtLeast(p2.y)
                    for (y in ys) containedPoints[Point2D(p1.x, y)] = true
                }
                p1.y == p2.y -> {
                    val xs = p1.x.coerceAtMost(p2.x)..p1.x.coerceAtLeast(p2.x)
                    for (x in xs) containedPoints[Point2D(x, p1.y)] = true
                }
            }
        }
    }

    private fun checkPoint(point: Point2D) = checkPoint(point.x, point.y)

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
                val Mx = maxOf(start.x, end.x)
                val mx = minOf(start.x, end.x)
                val My = maxOf(start.y, end.y)
                val my = minOf(start.y, end.y)

                if (!checkPoint(mx, my)
                    || !checkPoint(mx, My)
                    || !checkPoint(Mx, my)
                    || !checkPoint(Mx, My)
                ) return@maxOf 0

                if ((mx..Mx step 1500).any { x -> !checkPoint(x, my) })
                    return@maxOf 0

                if ((my..My step 1500).any { y -> !checkPoint(mx, y) })
                    return@maxOf 0

                (Mx - mx.toLong() + 1) * (My - my.toLong() + 1)
            }
        }
    }
}