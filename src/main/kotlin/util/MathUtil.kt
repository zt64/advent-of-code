package util

import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.sqrt

/**
 * Squares a number
 *
 * @return the square of this number
 */
fun Number.sq(): Double = this.toDouble() * this.toDouble()

fun Int.sq(): Int = this * this

fun sqrt(x: Int) = sqrt(x.toDouble())

fun Int.pow(exp: Int): Int {
    var result = 1
    var base = this
    var power = exp

    while (power > 0) {
        if (power % 2 == 1) result *= base
        base *= base
        power /= 2
    }

    return result
}

fun Number.digitCount(): Int = if (this == 0) 1 else floor(log10(this.toDouble())).toInt() + 1

fun Int.toDigits(base: Int = 10): List<Int> = buildList(digitCount()) {
    var n = this@toDigits
    require(n >= 0)
    while (n != 0) {
        add(n % base)
        n /= base
    }
}

fun Long.toDigits(base: Int = 10): List<Long> = buildList(digitCount()) {
    var n = this@toDigits
    require(n >= 0)
    while (n != 0L) {
        add(n % base)
        n /= base
    }
}

/**
 * Concatenate the provided numbers into a single number based on their position
 *
 * ```
 * concat(1, 2, 3) // 123
 * ```
 */
fun concat(vararg ints: Int): Int {
    var total = 0
    for (n in ints) {
        total = total * 10.pow(n.digitCount()) + n
    }
    return total
}