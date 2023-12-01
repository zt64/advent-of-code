import java.math.BigInteger

private object Day11 : Day(11) {
    private val monkeys = input.split("\n\n").map(String::lines).map {
        val operationString = it[2].substringAfter("= ")
        val (_, op, b) = operationString.split(" ")

        Monkey(
            startingItems = it[1].substringAfter("Starting items: ")
                .split(", ")
                .map(String::toBigInteger)
                .map(::Item)
                .toMutableList(),
            operation = when (op) {
                "+" -> { old -> old + (b.toBigIntegerOrNull() ?: old) }
                "*" -> { old -> old * (b.toBigIntegerOrNull() ?: old) }
                "-" -> { old -> old - (b.toBigIntegerOrNull() ?: old) }
                else -> throw Exception("Unknown operation $op")
            },
            test = Monkey.Test(
                divisibleBy = it[3].substringAfter("by ").toInt(),
                ifTrue = it[4].substringAfter("monkey ").toInt(),
                ifFalse = it[5].substringAfter("monkey ").toInt()
            )
        )
    }

    override fun part1(): BigInteger {
        repeat(20) {
            monkeys.forEach { monkey ->
                val copy = monkey.startingItems.toList()

                copy.forEach { item ->
                    item.worryLevel = monkey.operation(item.worryLevel)

                    monkey.inspections++

                    item.worryLevel = item.worryLevel / BigInteger.valueOf(3)

                    val passIndex = if (item.worryLevel % monkey.test.divisibleBy.toBigInteger() == BigInteger.ZERO) {
                        monkey.test.ifTrue
                    } else {
                        monkey.test.ifFalse
                    }

                    monkeys[passIndex].startingItems += item
                    monkey.startingItems.removeFirst()
                }
            }
        }

        val (monkey1, monkey2) = monkeys.sortedByDescending(Monkey::inspections).take(2)

        return monkey1.inspections * monkey2.inspections
    }

    override fun part2(): BigInteger {
        repeat(10000) {
            if (it % 100 == 0) println("Round $it")

            monkeys.forEach { monkey ->
                val copy = monkey.startingItems.toList()

                copy.forEach { item ->
                    item.worryLevel = monkey.operation(item.worryLevel)

                    monkey.inspections++

                    val passIndex = if (item.worryLevel % monkey.test.divisibleBy.toBigInteger() == BigInteger.ZERO) {
                        monkey.test.ifTrue
                    } else {
                        monkey.test.ifFalse
                    }

                    monkeys[passIndex].startingItems += item
                    monkey.startingItems.removeFirst()
                }
            }
        }

        val (monkey1, monkey2) = monkeys.sortedByDescending(Monkey::inspections).take(2)

        return monkey1.inspections * monkey2.inspections
    }

    data class Monkey(
        val startingItems: MutableList<Item>,
        val operation: (old: BigInteger) -> BigInteger,
        val test: Test,
        var inspections: BigInteger = BigInteger.ZERO
    ) {
        class Test(
            val divisibleBy: Int,
            val ifTrue: Int,
            val ifFalse: Int
        )
    }

    data class Item(var worryLevel: BigInteger)
}

private fun main() = Day11.main()