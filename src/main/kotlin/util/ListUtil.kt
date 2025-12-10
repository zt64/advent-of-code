package util

fun IntArray.mul(): Int {
    return reduce { acc, i -> acc * i }
}

fun Iterable<Int>.mul(): Int {
    return reduce { acc, i -> acc * i }
}

/**
 * Returns all permutations of the collection.
 *
 * ```
 * listOf(1, 2, 3).permutations() == [[1, 2, 3], [2, 1, 3], [2, 3, 1], [1, 3, 2], [3, 1, 2], [3, 2, 1]]
 * ```
 */
fun <E> Collection<E>.permutations(): List<List<E>> {
    if (size == 1) return listOf(toList())
    val element = first()
    return drop(1).permutations().flatMap { permutation ->
        (0..permutation.size).map { i ->
            permutation.toMutableList().apply { add(i, element) }
        }
    }
}
fun <T> List<T>.product(repeat: Int): Sequence<List<T>> =
    List(repeat) { this }.cartesianProductSeq()

fun <T> Collection<Collection<T>>.cartesianProductSeq(): Sequence<List<T>> = sequence {
    if (isEmpty()) {
        yield(emptyList())
        return@sequence
    }

    val lists = this@cartesianProductSeq.toList()
    val indices = IntArray(lists.size)

    while (true) {
        yield(lists.mapIndexed { i, list -> list.elementAt(indices[i]) })

        var carry = lists.lastIndex
        while (carry >= 0 && ++indices[carry] == lists[carry].size) {
            indices[carry] = 0
            carry--
        }

        if (carry < 0) break
    }
}

fun <E> List<E>.permutationsSeq(): Sequence<List<E>> = sequence {
    val arr = this@permutationsSeq.toMutableList()
    val n = arr.size
    val c = IntArray(n) { 0 }

    yield(arr.toList())

    var i = 0
    while (i < n) {
        if (c[i] < i) {
            if (i % 2 == 0) {
                arr[0] = arr[i].also { arr[i] = arr[0] }
            } else {
                arr[c[i]] = arr[i].also { arr[i] = arr[c[i]] }
            }

            yield(arr.toList())

            c[i]++
            i = 0
        } else {
            c[i] = 0
            i++
        }
    }
}


/**
 * Returns the Cartesian product of the collection of collections.
 *
 * ```
 * listOf(listOf(1, 2), listOf(3, 4)).cartesianProduct() == [[1, 3], [1, 4], [2, 3], [2, 4]]
 * ```
 */
fun <T> Collection<Collection<T>>.cartesianProduct(): List<List<T>> {
    if (isEmpty()) return emptyList()
    if (size == 1) return first().map { listOf(it) }
    val rest = drop(1).cartesianProduct()
    return first().flatMap { x -> rest.map { listOf(x) + it } }
}

/**
 * Returns all combinations of k elements from the list.
 *
 * @param k the number of elements in each combination
 *
 * ```
 * listOf(1, 2, 3, 4).combinations(2) == [[1, 2], [1, 3], [1, 4], [2, 3], [2, 4], [3, 4]]
 * ```
 */
fun <T> Collection<T>.combinations(k: Int): List<List<T>> {
    if (k == 0) return listOf(emptyList())
    if (k > size) return emptyList()
    val x = drop(1).combinations(k - 1).map { it + first() }
    val y = drop(1).combinations(k)
    return x + y
}