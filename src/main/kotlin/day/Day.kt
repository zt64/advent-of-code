package day

sealed class Day(val number: Int) {
    protected val input by lazy {
        javaClass.getResource(
            "/day${number.toString().padStart(2, '0')}.txt"
        )!!.readText()
    }

    abstract fun part1(): Any

    abstract fun part2(): Any
}