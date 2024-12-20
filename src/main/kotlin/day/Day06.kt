package day

import util.*

class Day06(input: String) : Day(6) {
    private val grid = input.to2DArray<Char>()

    // counts the number of points visited or returns -1 if the path is a loop
    private fun Array<Array<Char>>.countPoints(): Int {
        var position = findStart()
        var dir = Directions.North
        val visited = mutableSetOf(position to dir)

        while (true) {
            val next = position + dir

            if (this.getOrNull(next.y)?.getOrNull(next.x) == '#') {
                dir = Directions.CARDINALS.let { it[(it.indexOf(dir) + 1) % it.size] }

                continue
            }

            if (next.y !in this.indices || next.x !in this[next.y].indices) break

            if (!visited.add(next to dir)) return -1

            position = next
        }

        return visited.distinctBy { it.first }.size
    }

    override fun part1(): Any = grid.countPoints()

    override fun part2(): Any {
        var paths = 0
        val mutableGrid = grid

        grid.forEach2D { c, row, col ->
            if (c != '#') {
                val original = mutableGrid[row][col]
                mutableGrid[row][col] = '#'
                if (mutableGrid.countPoints() == -1) paths++
                mutableGrid[row][col] = original
            }
        }

        return paths
    }

    private fun findStart(): Point {
        val grid = input.lines()

        // find x and y of "^"
        grid.forEachIndexed { y, row ->
            row.forEachIndexed { x, c ->
                if (c == '^') return Point(x, y)
            }
        }

        error("Start not found")
    }
}