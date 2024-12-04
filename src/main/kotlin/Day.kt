sealed class Day(val number: Int) {
    protected val input = javaClass.getResource(
        "day${number.toString().padStart(2, '0')}.txt"
    )!!.readText()

    abstract fun part1(): Any

    open fun part2(): Any {
        return "Not implemented!"
    }
}