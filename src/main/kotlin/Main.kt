import day.*
import kotlinx.coroutines.*
import java.util.*
import kotlin.time.Duration
import kotlin.time.measureTimedValue

private const val YEAR = 2025

private val days = listOf(
    ::Day01,
    ::Day02,
    ::Day03,
    ::Day04,
    ::Day05,
    ::Day06,
    ::Day07,
    ::Day08,
    ::Day09,
    ::Day10,
    ::Day11,
    ::Day12
)

fun main(args: Array<String>) = runBlocking {
    val calendar = Calendar.getInstance()

    val dayIndex = if (calendar.get(Calendar.YEAR) == YEAR && args.isEmpty()) {
        calendar.get(Calendar.DAY_OF_MONTH)
    } else {
        if (args.isEmpty()) {
            println("Please provide a day number")
            return@runBlocking
        }

        args.first().toIntOrNull()
    }?.takeIf { i -> i in 1..days.size } ?: error("Invalid day")

    val dayConstructor = days.getOrNull(dayIndex - 1) ?: error("Day not found $dayIndex")

    println("\n🎄 AOC $YEAR Day ${dayIndex.toString().padStart(2, '0')} 🎄")
    println("═".repeat(30))
    println()

    withContext(Dispatchers.IO) {
        val paddedDay = dayIndex.toString().padStart(2, '0')
        val exampleInput = Day01::class.java.getResource("/day/$paddedDay/example.txt")?.readText()

        if (exampleInput != null) {
            println("Example Input:")
            val exampleDay = dayConstructor(exampleInput)
            runPart(1, exampleDay::part1)
            runPart(2, exampleDay::part2)
            println()
        }

        println("Real Input:")
        val realInput = Day01::class.java.getResource("/day/$paddedDay/input.txt")?.readText()
            ?: Day01::class.java.getResource("/day${paddedDay}.txt")?.readText()
            ?: error("Could not find input file for day $paddedDay")

        val realDay = dayConstructor(realInput)
        runPart(1, realDay::part1)
        runPart(2, realDay::part2)
    }
}

private suspend fun CoroutineScope.runPart(partNumber: Int, solve: () -> Any) {
    launch {
        val (result, duration) = measureTimedValue(solve)

        val formattedDuration = duration.formatNicely()
        println("\r* Part $partNumber (${formattedDuration.padStart(6)}): $result")
    }.join()
}

private fun Duration.formatNicely(): String = when {
    inWholeSeconds > 0 -> "%.2fs".format(inWholeMilliseconds / 1000.0)
    else -> "%.2fms".format(inWholeNanoseconds / 1_000_000.0)
}