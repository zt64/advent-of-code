package util

typealias Point = Pair<Int, Int>

val Point.x: Int get() = first
val Point.y: Int get() = second

val Pair<Long, Long>.x get() = first
val Pair<Long, Long>.y get() = second