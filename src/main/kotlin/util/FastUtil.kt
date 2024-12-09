package util

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@Suppress("BanInlineOptIn")
@OptIn(ExperimentalContracts::class)
inline fun <T> List<T>.fastForEach(action: (T) -> Unit) {
    contract { callsInPlace(action) }
    for (index in indices) {
        val item = get(index)
        action(item)
    }
}

inline fun <T> Array<T>.fastForEachIndexed(action: (index: Int, T) -> Unit) {
    var i = 0
    while (i < size) action(i, this[i++])
}

@Suppress("BanInlineOptIn")
@OptIn(ExperimentalContracts::class)
inline fun <T, R> List<T>.fastMap(transform: (T) -> R): List<R> {
    contract { callsInPlace(transform) }
    val target = ArrayList<R>(size)
    fastForEach { target += transform(it) }
    return target
}

@Suppress("BanInlineOptIn")
@OptIn(ExperimentalContracts::class)
inline fun <T, R> List<T>.fastMapNotNull(transform: (T) -> R?): List<R> {
    contract { callsInPlace(transform) }
    val target = ArrayList<R>(size)
    fastForEach { e -> transform(e)?.let { target += it } }
    return target
}