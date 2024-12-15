package util

typealias Grid<T> = Array<Array<T>>

inline fun <reified T> Grid(h: Int, w: Int, init: (Int, Int) -> T): Grid<T> {
    return Array(h) { i -> Array(w) { j -> init(i, j) } }
}

inline fun <reified T> Grid(h: Int, w: Int, init: () -> T): Grid<T> {
    return Array(h) { i -> Array(w) { j -> init() } }
}

inline fun <reified T : Any> String.to2DArray(splitter: (Char) -> T = { it as T }): Grid<T> {
    return lines().map { it.map(splitter).toTypedArray() }.toTypedArray()
}

inline fun <T> Grid<T>.forEach2D(action: (T, row: Int, col: Int) -> Unit) {
    forEachIndexed { i, row -> row.forEachIndexed { j, value -> action(value, i, j) } }
}

inline fun <reified T> Grid<T>.map2D(transform: (T, row: Int, col: Int) -> T): Grid<T> {
    return mapIndexed2D { _, value, row, col -> transform(value, row, col) }
}

inline fun <reified T, reified R> Grid<T>.mapIndexed2D(transform: (index: Int, T, row: Int, col: Int) -> R): Grid<R> {
    return Array(size) { i -> Array(this[i].size) { j -> transform(i, this[i][j], i, j) } }
}

inline fun <reified T, reified R> Grid<T>.mapIndexedNotNull2D(transform: (T, row: Int, col: Int) -> R?): Grid<R> {
    return Array(size) { i ->
        Array(this[i].size) { j ->
            transform(this[i][j], i, j)
        }.filterNotNull().toTypedArray()
    }
}

operator fun <T> Grid<T>.get(x: Int, y: Int): T = this[x][y]

operator fun <T> Grid<T>.get(pair: Pair<Int, Int>): T = this[pair.first][pair.second]

operator fun <T> Grid<T>.get(value: T): Pair<Int, Int>? {
    forEachIndexed { i, row ->
        row.forEachIndexed { j, v ->
            if (v == value) return i to j
        }
    }
    return null
}

fun <T> Grid<T>.getOrNull(x: Int, y: Int): T? = this.getOrNull(x)?.getOrNull(y)

fun <T> Grid<T>.getOrNull(x: Int, y: Int, direction: Direction): T? {
    val (dy, dx) = direction.offset
    return getOrNull(x + dx, y + dy)
}

operator fun <T> Grid<T>.contains(pair: Pair<Int, Int>): Boolean =
    pair.first in indices && pair.second in this[pair.first].indices

operator fun <T> Grid<T>.set(x: Int, y: Int, value: T) {
    this[x][y] = value
}

operator fun <T> Grid<T>.set(pair: Pair<Int, Int>, value: T) {
    this[pair.first][pair.second] = value
}