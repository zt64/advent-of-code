package day

import util.*
import java.util.*

enum class Movement(val offset: Pair<Int, Int>) {
    UP(-1 to 0),
    DOWN(1 to 0),
    LEFT(0 to -1),
    RIGHT(0 to 1);
}

object Day15 : Day(15) {
    private val movements = input.substringAfter("\n\n").replace("\n", "").map {
        when (it) {
            '^' -> Movement.UP
            'v' -> Movement.DOWN
            '<' -> Movement.LEFT
            '>' -> Movement.RIGHT
            else -> throw IllegalArgumentException("Invalid movement: $it")
        }
    }

    override fun part1(): Any {
        val map = input.substringBefore("\n\n").to2DArray<Char>()
        var pos = map.getStartPos()

        map[pos.first][pos.second] = '.'

        for (m in movements) {
            var next = pos + m.offset

            when (map[next]) {
                '.' -> pos = next
                'O' -> {
                    var finalPos = next

                    while (map[finalPos] == 'O') finalPos += m.offset

                    if (map[finalPos] != '.') continue

                    map[next] = '.'
                    map[finalPos] = 'O'
                    pos = next
                }
            }
        }

        return map.countCoordinates('O')
    }

    override fun part2(): Any {
        var map = input
            .substringBefore("\n\n")
            .replace("#", "##")
            .replace("O", "[]")
            .replace(".", "..")
            .replace("@", "@.")
            .to2DArray<Char>()

        var pos = map.getStartPos()

        map[pos.first][pos.second] = '.'

        for (m in movements) {
            val offset = m.offset
            var next = pos + offset

            when  {
                map[next] == '.' -> pos = next
                map[next].let { it == '[' || it == ']' } -> {
                    val queue: Queue<Pair<Int, Int>> = LinkedList()

                    queue.add(next)

                    val seen = mutableSetOf<Pair<Int, Int>>()

                    while (queue.isNotEmpty()) {
                        val p = queue.poll()
                        if (map[p] !in "[]") error("death")
                        if (!seen.add(p)) continue

                        when (map[p]) {
                            '[' -> queue.add(p + Movement.RIGHT.offset)
                            ']' -> queue.add(p + Movement.LEFT.offset)
                        }

                        if (map[p + offset] in "[]") queue.add(p + offset)
                    }

                    if (seen.any { map[it + offset] == '#'}) continue

                    seen.associateWith { map[it] }
                        .also { it.forEach { (p, _) -> map[p] = '.' } }
                        .forEach { (p, c) -> map[p + offset] = c }

                    pos = next
                }
            }
        }

        return map.countCoordinates('[')
    }

    private fun Grid<Char>.countCoordinates(char: Char): Int {
        var n = 0
        forEach2D { c, row, col -> if (c == char) n += 100 * row + col }
        return n
    }

    private fun Grid<Char>.getStartPos(): Pair<Int, Int> {
        forEachIndexed { i, row ->
            row.forEachIndexed { j, c ->
                if (c == '@') return i to j
            }
        }
        throw IllegalArgumentException("No start position found")
    }
}