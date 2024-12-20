package day

import util.mul
import util.x
import util.y

object Day14 : Day(14) {
    private val regex = """p=(\d+),(\d+) v=(-?\d+),(-?\d+)""".toRegex()

    private val robots = input.lines().map { line ->
        regex.matchEntire(line)!!.destructured.let { (px, py, vx, vy) ->
            Robot(px.toInt() to py.toInt(), vx.toInt() to vy.toInt())
        }
    }

    private val width = robots.maxOf { it.pos.x } + 1
    private val height = robots.maxOf { it.pos.y } + 1

    private fun Robot.newPosition(times: Int = 1): Pair<Int, Int> {
        val newX = (pos.x + velocity.x * times).mod(width)
        val newY = (pos.y + velocity.y * times).mod(height)
        return newX to newY
    }

    override fun part1(): Any {
        val halfHeight = height / 2
        val halfWidth = width / 2

        val quadrantCounts = IntArray(4)

        robots
            .map { it.copy(pos = it.newPosition(100)) }
            .forEach { (pos) ->
                when {
                    pos.x == halfWidth || pos.y == halfHeight -> return@forEach
                    pos.y < halfHeight && pos.x < halfWidth -> quadrantCounts[0]++
                    pos.y < halfHeight && pos.x >= halfWidth -> quadrantCounts[1]++
                    pos.y >= halfHeight && pos.x < halfWidth -> quadrantCounts[2]++
                    pos.y >= halfHeight && pos.x >= halfWidth -> quadrantCounts[3]++
                }
            }

        return quadrantCounts.mul()
    }

    override fun part2(): Any {
        var i = 0
        val seenPositions = mutableSetOf<Pair<Int, Int>>()

        while (true) {
            seenPositions.clear()
            var allUnique = true

            robots.forEach { r ->
                r.pos = r.newPosition()

                if (!seenPositions.add(r.pos)) allUnique = false
            }

            i++

            if (allUnique) break
        }

        return i
    }

    private data class Robot(var pos: Pair<Int, Int>, val velocity: Pair<Int, Int>)
}