private typealias Equation = Pair<Long, List<Long>>

object Day07 : Day(7) {
    private val equations = input.lines().map {
        val expected = it.substringBefore(":")
        val values = it.substringAfter(": ").split(" ")

        expected.toLong() to values.map { it.toLong() }
    }

    private var operators: List<(Long, Long) -> Long> = mutableListOf(
        { a, b -> a + b },
        { a, b -> a * b }
    )

    private fun Equation.isValid(): Boolean {
        val (expected, values) = this

        val prevValues = mutableListOf(values.first())
        val calculated = mutableListOf<Long>()

        values.drop(1).forEach { r ->
            prevValues.forEach { l ->
                operators.forEach { op ->
                    val value = op(l, r)
                    if (value <= expected) calculated += value
                }
            }

            prevValues.clear()
            prevValues.addAll(calculated)
            calculated.clear()

            if (expected in prevValues) return true
        }

        return false
    }

    private fun countValidEquations(): Long {
        return equations.sumOf { eq -> if (eq.isValid()) eq.first else 0 }
    }

    override fun part1(): Any = countValidEquations()

    override fun part2(): Any {
        operators += { a, b -> "$a$b".toLong() }
        return countValidEquations()
    }
}