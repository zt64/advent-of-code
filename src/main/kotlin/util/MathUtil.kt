package util

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