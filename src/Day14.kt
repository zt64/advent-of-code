private object Day14 : Day(14) {
    private val grid = MutableList(1000) { y ->
        MutableList<Point>(1000) { x -> Air(x, y) }
    }

    private val directionPairs = listOf(
        0 to 1, // Down
        -1 to 1, // Down-left
        1 to 1 // Down-right
    )

    private val spawnPoint = Point(500, 0)

    init {
        val paths = input.lines().map { path ->
            path.replace(" ", "")
                .split("->")
                .map { pair ->
                    val (x, y) = pair.split(",")

                    Point(x.toInt(), y.toInt())
                }
        }

        paths.forEach { path ->
            path.windowed(2) { (previousPoint, currentPoint) ->
                val xRange = if (previousPoint.x < currentPoint.x) {
                    previousPoint.x..currentPoint.x
                } else {
                    currentPoint.x..previousPoint.x
                }

                val yRange = if (previousPoint.y < currentPoint.y) {
                    previousPoint.y..currentPoint.y
                } else {
                    currentPoint.y..previousPoint.y
                }

                xRange.forEach { x ->
                    yRange.forEach { y ->
                        grid[y][x] = Rock(x, y)
                    }
                }
            }
        }
    }

    private fun simulate(): Int {
        var restingSand = 0
        var hitAbyss = false

        while (!hitAbyss) {
            if (grid[spawnPoint.y][spawnPoint.x] !is Air) return restingSand

            var sand = Sand(spawnPoint.x, spawnPoint.y)
            grid[sand.y][sand.x] = sand

            while (true) {
                try {
                    if (
                        directionPairs.none { (dx, dy) ->
                            val (x, y) = sand.x + dx to sand.y + dy

                            if (grid[y][x] is Air) {
                                grid[sand.y][sand.x] = Air(sand.x, sand.y)

                                sand = Sand(x, y)
                                grid[y][x] = sand

                                true
                            } else false
                        }
                    ) {
                        restingSand++
                        break
                    }
                } catch (e: IndexOutOfBoundsException) {
                    hitAbyss = true
                    break
                }
            }
        }

        return restingSand
    }

    override fun part1() = simulate()

    override fun part2(): Int {
        // Reset to initial state
        grid.forEach { row ->
            row.replaceAll {
                if (it is Sand) Air(it.x, it.y) else it
            }
        }

        // Find the lowest rock + 2
        val groundRowIndex = 2 + grid.indexOfLast { row ->
            row.any { it is Rock }
        }

        // Set entire ground row to rock
        grid[groundRowIndex].indices.forEach { x ->
            grid[groundRowIndex][x] = Rock(x, groundRowIndex)
        }

        return simulate()
    }

    private open class Point(val x: Int, val y: Int)
    private class Air(x: Int, y: Int) : Point(x, y)
    private class Sand(x: Int, y: Int) : Point(x, y)
    private class Rock(x: Int, y: Int) : Point(x, y)
}

private fun main() = Day14.main()