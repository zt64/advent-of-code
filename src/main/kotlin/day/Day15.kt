package day

import util.*
import java.util.*

private enum class Movement(val offset: Pair<Int, Int>) {
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

        return countCoordinates(map)
    }

    override fun part2(): Any {
        var map = input
            .substringBefore("\n\n")
            .replace("#", "##")
            .replace("O", "[]")
            .replace(".", "..")
            .replace("@", "@.")
            .to2DArray<Char>()

        return countCoordinates(map)
    }

    private fun countCoordinates(map: Grid<Char>): Int {
        var pos = map['@']!!
        map[pos] = '.'

        movement@ for (m in movements) {
            val offset = m.offset
            var next = pos + offset

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
                '[', ']' -> {
                    val s = Stack<Pair<Int, Int>>().apply { add(next) }
                    val seen = mutableSetOf<Pair<Int, Int>>()

                    while (s.isNotEmpty()) {
                        val p = s.removeLast()
                        if (!seen.add(p)) continue

                        when (map[p]) {
                            '[' -> s.add(p + Movement.RIGHT.offset)
                            ']' -> s.add(p + Movement.LEFT.offset)
                        }

                        when (map[p + offset]) {
                            '[', ']' -> s.add(p + offset)
                            '#' -> continue@movement
                        }
                    }

                    if (seen.any { map[it + offset] == '#' }) continue

                    seen.associateWith { map[it] }
                        .onEach { (p, _) -> map[p] = '.' }
                        .forEach { (p, c) -> map[p + offset] = c }

                    pos = next
                }
            }
        }

        var n = 0
        map.forEach2D { c, row, col -> if (c == '[' || c == 'O') n += 100 * row + col }
        return n
    }
}