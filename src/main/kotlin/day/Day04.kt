package day

import util.*

class Day04(input: String) : Day(input) {
    private val grid = input.to2DArray<Char>()

    private val positions = listOf(
        listOf(-1, -1), // top left
        listOf(0, -1), // top middle
        listOf(1, -1), // top right
        listOf(-1, 0), // center left
        listOf(1, 0), // center right
        listOf(-1, 1), // bottom left
        listOf(0, 1), // bottom center
        listOf(1, 1), // bottom right
    )

    private fun Grid<Char>.countNeighboringRolls(x: Int, y: Int): Int {
        return positions.count { (xOffset, yOffset) ->
            val newX = xOffset + x
            val newY = yOffset + y
            newX in 0..<grid.width && newY in 0..<grid.height && this[newY, newX] == '@'
        }
    }

    override fun part1(): Any {
        var n = 0
        grid.forEachIndexed { y, row ->
            row.forEachIndexed { x, item ->
                if (item == '.') return@forEachIndexed

                if (grid.countNeighboringRolls(x, y) < 4) n++
            }
        }

        return n
    }

    override fun part2(): Any {
        var tmp = grid.copyOf()
        var totalRemoved = 0

        while (true) {
            val next = tmp.copyOf()
            var removed = 0

            tmp.forEachIndexed { y, row ->
                row.forEachIndexed { x, item ->
                    if (item == '.') return@forEachIndexed

                    if (tmp.countNeighboringRolls(x, y) < 4) {
                        next[y][x] = '.'
                        removed++
                    }
                }
            }

            if (removed == 0) break
            totalRemoved += removed
            tmp = next
        }

        return totalRemoved
    }
}