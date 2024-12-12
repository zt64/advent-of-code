package util

typealias Point = Pair<Int, Int>

val Point.x: Int get() = first
val Point.y: Int get() = second

operator fun Point.plus(other: Point): Point = first + other.first to second + other.second