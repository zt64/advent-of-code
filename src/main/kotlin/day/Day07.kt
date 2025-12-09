package day

import util.Point2D
import util.getOrNull
import util.to2DArray

class Day07(input: String) : Day(input) {
    override fun part1(): Any {
        val grid = input.to2DArray<Char>()
        var splits = 0
        grid.dropLast(1).forEachIndexed { row, tiles ->
            tiles.forEachIndexed { tileI, tile ->
                if (tile == 'S' || tile == '|') {
                    if (grid[row + 1][tileI] == '^') {
                        splits++
                        grid[row + 1][tileI - 1] = '|'
                        grid[row + 1][tileI + 1] = '|'
                    } else {
                        grid[row + 1][tileI] = '|'
                    }
                }
            }
        }
        return splits
    }

    override fun part2(): Any {
        val grid = input.to2DArray<Char>()
        val cache = HashMap<Point2D, Long>()

        fun count(curr: Point2D): Long = cache.getOrPut(curr) {
            when {
                grid.getOrNull(curr.down) == '^' -> count(curr.left) + count(curr.right)
                grid.getOrNull(curr.down) == '.' -> count(curr.down)
                else -> 1
            }
        }

        return count(Point2D(grid.first().indexOf('S'), 0))
    }
}