package util

fun IntArray.mul(): Int {
    return reduce { acc, i -> acc * i }
}

fun Iterable<Int>.mul(): Int {
    return reduce { acc, i -> acc * i }
}