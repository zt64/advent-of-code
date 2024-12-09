package util

typealias Grid<T> = Array<Array<T>>

inline fun <reified T : Any> String.to2DArray(spliter: (Char) -> T = { it as T }): Grid<T> {
    return lines().map { it.map(spliter).toTypedArray() }.toTypedArray()
}

fun <T> Grid<T>.forEach2D(action: (T, row: Int, col: Int) -> Unit) {
    indices.forEach { i ->
        this[i].indices.forEach { j -> action(this[i][j], i, j) }
    }
}

operator fun <T> Grid<T>.get(x: Int, y: Int): T = this[x][y]

fun <T> Grid<T>.getOrNull(x: Int, y: Int): T? = if (x in indices && y in this[x].indices) this[x][y] else null

operator fun <T> Grid<T>.contains(pair: Pair<Int, Int>): Boolean = pair.first in indices && pair.second in this[pair.first].indices

operator fun <T> Grid<T>.set(x: Int, y: Int, value: T) {
    this[x][y] = value
}