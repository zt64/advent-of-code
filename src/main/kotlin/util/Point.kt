package util

import kotlin.math.abs

typealias Point = Pair<Int, Int>

val Point.x: Int get() = first
val Point.y: Int get() = second

val Pair<Long, Long>.x get() = first
val Pair<Long, Long>.y get() = second

val Point.cardinalNeighbors: List<Point>
    get() = Directions.CARDINALS.map { this + it }

fun Point.manhattanDistance(other: Point): Int = abs(x - other.x) + abs(y - other.y)