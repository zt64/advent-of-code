package day

private enum class Operation {
    ADD, MUL
}

private data class Problem(
    val numbers: List<Long>,
    val operation: Operation
)

class Day06(input: String) : Day(input) {
    private fun calculateGrandTotal(problems: List<Problem>): Long {
        return problems.sumOf {
            when (it.operation) {
                Operation.ADD -> it.numbers.sum()
                Operation.MUL -> it.numbers.reduce { acc, i -> acc * i }
            }
        }
    }

    override fun part1(): Any {
        val problems = run {
            val lines = input.lines().map { it.split(" ").filter { it.isNotBlank() } }
            val (numberRows, operationRow) = lines.dropLast(1) to lines.last()

            operationRow.indices.map { col ->
                Problem(
                    numbers = numberRows.map { it[col].toLong() },
                    operation = if (operationRow[col] == "*") Operation.MUL else Operation.ADD
                )
            }
        }

        return calculateGrandTotal(problems)
    }

    override fun part2(): Any {
        val problems = run {
            val lines = input.lines().map { it.trim().split("\\s+".toRegex()) }
            val (numberRows, operationRow) = lines.dropLast(1).flatMap { it.map { it.padStart(3, '0').toList() } } to lines.last()

            val strings = numberRows.map { it.toString().padStart(3, '0') }
            val cols = strings[0].length

            println(strings)

            val transposedNumberRows = List(cols) { c ->
                strings.joinToString("") { it[c].toString() }
            }


        }

        return 0
        // return calculateGrandTotal(problems)
    }
}