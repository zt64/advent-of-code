data class Point(val x: Int, val y: Int)

object Day06 : Day(6) {
    private val grid = input.to2DArray<Char>()

    private enum class Direction {
        UP, RIGHT, DOWN, LEFT
    }

    // counts the number of points visited or returns -1 if the path is a loop
    private fun List<List<Char>>.countPoints(): Int {
        var position = findStart()
        var dir = Direction.UP
        val visited = mutableSetOf(position to dir)

        while (true) {
            val next = when (dir) {
                Direction.UP -> Point(position.x, position.y - 1)
                Direction.RIGHT -> Point(position.x + 1, position.y)
                Direction.DOWN -> Point(position.x, position.y + 1)
                Direction.LEFT -> Point(position.x - 1, position.y)
            }

            if (this.getOrNull(next.y)?.getOrNull(next.x) == '#') {
                dir = Direction.entries.let { it[(it.indexOf(dir) + 1) % it.size] }

                continue
            }

            if (next.y !in this.indices || next.x !in this[next.y].indices) break

            if (!visited.add(next to dir)) return -1

            position = next
        }

        return visited.distinctBy { it.first }.size
    }

    override fun part1(): Any {
        return grid.countPoints()
    }

    override fun part2(): Any {
        var paths = 0
        val mutableGrid = grid.map { it.toMutableList() }

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