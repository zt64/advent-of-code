inline fun <reified T : Any> String.to2DArray(mappingFunction: (Char) -> T): Array<Array<T>> {
    return lines().map { line ->
        line.map { mappingFunction(it) }.toTypedArray()
    }.toTypedArray()
}

fun <T> Array<Array<T>>.forEach2D(action: (T, row: Int, col: Int) -> Unit) {
    indices.forEach { i ->
        this[i].indices.forEach { j -> action(this[i][j], i, j) }
    }
}