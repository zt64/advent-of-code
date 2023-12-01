import java.util.*

private object Day12 : Day(12) {
    private data class Point(val row: Int, val col: Int, var height: Char) {
        var visited: Boolean = false
        var dist: Int = 0
    }

    private lateinit var startPoint: Point
    private lateinit var endPoint: Point

    private var heightmap = input.lines().mapIndexed { rowIndex, chars ->
        chars.toCharArray().mapIndexed { columnIndex, height ->
            Point(rowIndex, columnIndex, height).also { point ->
                when (height) {
                    'S' -> {
                        point.height = 'a'
                        startPoint = point
                    }

                    'E' -> {
                        point.height = 'z'
                        endPoint = point
                    }
                }
            }
        }
    }

    private val directionVectors = listOf(
        1 to 0,     // down
        -1 to 0,    // up
        0 to 1,     // right
        0 to -1     // left
    )

    private fun findShortestPath(startingPoint: Point): Int {
        val queue = LinkedList<Point>()

        startingPoint.visited = true
        queue += startingPoint

        while (queue.isNotEmpty()) {
            val currentPoint = queue.remove()

            if (currentPoint == endPoint) return currentPoint.dist

            directionVectors.forEach { (rowDelta, colDelta) ->
                val next = heightmap
                    .getOrNull(currentPoint.row + rowDelta)
                    ?.getOrNull(currentPoint.col + colDelta)
                    ?: return@forEach

                if (next.visited || next.height > currentPoint.height + 1) return@forEach

                next.dist = currentPoint.dist + 1
                next.visited = true
                queue += next
            }
        }

        throw Exception("No path found")
    }

    override fun part1(): Int = findShortestPath(startPoint)

    override fun part2(): Int = heightmap
        .onEach { row ->
            row.forEach {
                it.visited = false
                it.dist = 0
            }
        }
        .flatMap { row -> row.filter { it.height == 'a' } }
        .mapNotNull {
            it.runCatching { findShortestPath(it) }.getOrNull()
        }
        .min()
}

private fun main() = Day12.main()