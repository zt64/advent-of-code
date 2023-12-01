import kotlin.math.absoluteValue

private object Day21 : Day(21) {
    private val monkeys = input.lines().associate { line ->
        val (name, job) = line.split(": ")

        val split = job.split(" ")

        val function = if (split.size == 1) {
            Job.Number(job.toLong())
        } else {
            Job.Operation(split[1], split[0], split[2])
        }

        name to function
    }.toMutableMap()

    private fun solve(monkeyName: String): Long {
        return when (val job = monkeys[monkeyName]!!) {
            is Job.Number -> job.value
            is Job.Operation -> when (job.op) {
                "+" -> solve(job.a) + solve(job.b)
                "-" -> solve(job.a) - solve(job.b)
                "*" -> solve(job.a) * solve(job.b)
                "/" -> solve(job.a) / solve(job.b)
                else -> throw IllegalArgumentException("Unknown op: ${job.op}")
            }
        }
    }

    override fun part1(): Long = solve("root")

    override fun part2(): Long {
        val job = monkeys["root"]!! as Job.Operation
        var crack = 0L

        while (true) {
            monkeys["humn"] = Job.Number(crack)

            val a = solve(job.a)
            val b = solve(job.b)

            if (a == b) break

            val diff = (a - b).absoluteValue

            if (diff < 100) crack++ else crack += diff / 100
        }

        return crack
    }

    private sealed interface Job {
        data class Number(val value: Long) : Job
        data class Operation(val op: String, val a: String, val b: String) : Job
    }
}

private fun main() = Day21.main()