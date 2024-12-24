package util

/**
 * Splits the string at the given index.
 *
 * @param index The index to split the string at.
 *
 * @return A pair of the first and second part of the string.
 */
fun String.splitAtIndex(index: Int): Pair<String, String> {
    return take(index) to drop(index)
}

/**
 * Splits the string in half.
 *
 * @return A pair of the first and second half of the string.
 */
fun String.halve(): Pair<String, String> = splitAtIndex(length / 2)

fun String.substringBetween(start: String, end: String): String {
    return substringAfter(start).substringBefore(end)
}

fun String.permutations(): List<String> {
    when (length) {
        0 -> return emptyList()
        1 -> return listOf(this)
    }

    val element = first()
    return drop(1).permutations().flatMap { permutation ->
        (0..permutation.length).map { i ->
            permutation.toMutableList().apply { add(i, element) }.joinToString("")
        }
    }.distinct()
}