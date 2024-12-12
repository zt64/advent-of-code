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