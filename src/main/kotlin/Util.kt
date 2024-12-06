inline fun <reified T : Any> String.to2DArray(spliter: (Char) -> T = { it as T }): List<List<T>> {
    return this.lines().map { it.map(spliter) }
}

fun <T> List<List<T>>.forEach2D(action: (T, row: Int, col: Int) -> Unit) {
    indices.forEach { i ->
        this[i].indices.forEach { j -> action(this[i][j], i, j) }
    }
}