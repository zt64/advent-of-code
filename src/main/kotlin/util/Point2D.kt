package util

import kotlin.math.abs

data class Point2D(val x: Int, val y: Int) {
    operator fun plus(other: Point2D) = Point2D(x + other.x, y + other.y)
    operator fun minus(other: Point2D) = Point2D(x - other.x, y - other.y)
    operator fun times(scalar: Int) = Point2D(x * scalar, y * scalar)
    operator fun div(scalar: Int) = Point2D(x / scalar, y / scalar)
    operator fun rem(scalar: Int) = Point2D(x % scalar, y % scalar)

    inline val up get() = Point2D(x, y - 1)
    inline val down get() = Point2D(x, y + 1)
    inline val left get() = Point2D(x - 1, y)
    inline val right get() = Point2D(x + 1, y)
}

// val Point2D.cardinalNeighbors: List<Point>
//     get() = Directions.CARDINALS.map { this + it }

fun Point2D.manhattanDistance(other: Point2D): Int = abs(x - other.x) + abs(y - other.y)