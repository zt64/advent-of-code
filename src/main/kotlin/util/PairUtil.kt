package util

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> = first + other.first to second + other.second

operator fun Pair<Int, Int>.minus(other: Pair<Int, Int>): Pair<Int, Int> = first - other.first to second - other.second

operator fun Pair<Int, Int>.times(other: Pair<Int, Int>): Pair<Int, Int> = first * other.first to second * other.second

operator fun Pair<Int, Int>.div(other: Pair<Int, Int>): Pair<Int, Int> = first / other.first to second / other.second