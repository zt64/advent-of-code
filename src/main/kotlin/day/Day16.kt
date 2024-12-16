package day

import util.*
import util.Point
import java.util.*

object Day16 : Day(16) {
    private data class Tile(val pos: Point, val score: Int, val dir: Direction)

    private val map = input.to2DArray<Char>()
    private val start = map['S']!!.apply { map[this] = '.' }
    private val end = map['E']!!.apply { map[this] = '.' }

    override fun part1(): Any {
        val seen = mutableSetOf<Pair<Point, Direction>>()
        val queue = PriorityQueue<Tile>(compareBy { it.score })
        queue.add(Tile(start, 0, Direction.EAST))

        while (queue.isNotEmpty()) {
            val (pos, score, dir) = queue.poll()

            if (pos == end) return score

            listOf(dir, dir.ccw(), dir.cw()).forEach { newDir ->
                val next = pos + newDir

                if (map[next] != '#') {
                    if (!seen.add(next to newDir)) return@forEach

                    queue.add(
                        Tile(
                            pos = next,
                            score = score + if (dir == newDir) 1 else 1001,
                            dir = newDir
                        )
                    )
                }
            }
        }

        error("No path found")
    }

    private data class Path(
        val points: List<Point>,
        val score: Int,
        val dir: Direction
    )

    // fuck this
    override fun part2(): Any {
        var min = Int.MAX_VALUE
        val seen = mutableMapOf<Pair<Point, Direction>, Int>()
        val best = mutableSetOf<Point>()
        val queue = PriorityQueue<Path>(compareBy { it.score })
        queue.add(Path(listOf(start), 0, Direction.EAST))

        while (queue.isNotEmpty()) {
            val path = queue.poll()
            val (points, score, dir) = path
            val current = points.last()

            if (current == end) {
                if (score <= min) min = score else break

                best.addAll(points)
            }
            if (seen[current to dir] != null && seen[current to dir]!! < score) continue
            seen[current to dir] = score

            if (map[current + dir] != '#') {
                queue.add(
                    path.copy(
                        points = points + (current + dir),
                        score = score + 1
                    )
                )
            }

            queue += path.copy(score = score + 1000, dir = dir.ccw())
            queue += path.copy(score = score + 1000, dir = dir.cw())
        }

        println(min)

        return best.size
    }
}

operator fun Point.plus(direction: Direction): Point {
    val (dx, dy) = direction.offset
    return first + dy to second + dx
}