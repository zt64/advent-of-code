private object Day10 : Day(10) {
    private val instructions = input.lines().map {
        when (it.substringBefore(" ")) {
            "addx" -> Instruction.Add(it.substringAfter(" ").toInt())
            else -> Instruction.Noop
        }
    }

    private val stack = mutableMapOf<Int, Int>().toSortedMap()

    private fun createStack() {
        var index = 1

        instructions.forEach { instruction ->
            when (instruction) {
                is Instruction.Add -> {
                    index += 2

                    stack[index] = instruction.value
                }

                Instruction.Noop -> {
                    index += 1

                    stack[index] = 0
                }
            }
        }

        (1..240).forEach {
            if (it !in stack) stack[it] = 0
        }

        stack.remove(241)
    }

    override fun part1(): Int {
        createStack()

        var register = 1
        var sum = 0

        stack.forEach { (cycle, delta) ->
            register += delta

            if ((cycle + 20) % 40 == 0) sum += cycle * register
        }

        return sum
    }

    override fun part2(): String {
        var register = 1

        return buildString {
            stack.forEach { (cycle, delta) ->
                register += delta

                val pixel = (cycle - 1) % 40

                if (pixel == 0) appendLine()

                val sprite = register - 1..register + 1

                append(if (pixel in sprite) "#" else ".")
            }
        }
    }

    private sealed interface Instruction {
        @JvmInline
        value class Add(val value: Int) : Instruction
        object Noop : Instruction
    }
}

fun main() = Day10.main()