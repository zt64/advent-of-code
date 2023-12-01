import kotlin.math.absoluteValue

private object Day9 : Day(9) {
    private val visitedPoints = hashSetOf<Point>()
    private val motions = input.lines().map {
        val (direction, steps) = it.split(" ")

        Direction[direction] to steps.toInt()
    }

    override fun part1(): Int {
        var head = Point(0, 0)
        var tail = Point(0, 0)

        visitedPoints += head

        motions.forEach { (direction, steps) ->
            for (index in 0..steps) {
                val (x, y) = when (direction) {
                    Direction.UP -> 0 to 1
                    Direction.DOWN -> 0 to -1
                    Direction.LEFT -> -1 to 0
                    Direction.RIGHT -> 1 to 0
                }

                head = head.move(x, y)

                val touching = (head.x - tail.x).absoluteValue < 2 && (head.y - tail.y).absoluteValue < 2

                if (touching) {
                    break
                }
                tail = tail.move(x, y)
                visitedPoints += tail
            }
        }


        println(visitedPoints)

        return visitedPoints.distinct().size
    }

    override fun part2(): Int = TODO("Not yet implemented")

    private enum class Direction(private val letter: String) {
        UP("U"),
        DOWN("D"),
        LEFT("L"),
        RIGHT("R");

        companion object {
            operator fun get(letter: String) = values().first { it.letter == letter }
        }
    }

    private data class Point(val x: Int, val y: Int) {
        fun move(x: Int, y: Int) = Point(this.x + x, this.y + y)
    }
}

private fun main() = Day9.main()