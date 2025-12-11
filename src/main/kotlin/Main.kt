
import day.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.time.Duration
import kotlin.time.measureTimedValue

private const val YEAR = 2025

private val days = listOf(
    ::Day01, ::Day02, ::Day03, ::Day04, ::Day05, ::Day06,
    ::Day07, ::Day08, ::Day09, ::Day10, ::Day11, ::Day12
)

fun main(args: Array<String>) = runBlocking {
    val dayIndex = if (args.isEmpty()) {
        val calendar = Calendar.getInstance()
        if (calendar.get(Calendar.YEAR) == YEAR) {
            calendar.get(Calendar.DAY_OF_MONTH)
        } else {
            error("Please provide a day number")
        }
    } else {
        args.first().toIntOrNull()?.takeIf { it in 1..days.size }
            ?: error("Invalid day: ${args.first()}")
    }

    val dayConstructor = days.getOrNull(dayIndex - 1) ?: error("Day not found: $dayIndex")
    val paddedDay = dayIndex.toString().padStart(2, '0')

    println("\n🎄 AOC $YEAR Day ${dayIndex.toString().padStart(2, '0')} 🎄")
    println("═".repeat(30))
    println()

    withContext(Dispatchers.IO) {
        loadInput(paddedDay, "example.txt")?.let { exampleInput ->
            println("Example Input:")
            runDay(dayConstructor(exampleInput))
            println()
        }

        println("Real Input:")
        val realInput = args.getOrNull(1)?.let { Path(it).readText() }
            ?: loadInput(paddedDay, "input.txt")
            ?: error("Could not find input file for day $paddedDay")

        runDay(dayConstructor(realInput))
    }
}

private fun loadInput(paddedDay: String, filename: String): String? {
    return Day::class.java.getResource("/day/$paddedDay/$filename")?.readText()
}

private fun runDay(day: Day) {
    runPart(1) { day.part1() }
    runPart(2) { day.part2() }
}

private fun runPart(partNumber: Int, solve: () -> Any) {
    val (result, duration) = measureTimedValue(solve)
    val formattedDuration = duration.formatNicely()
    println("* Part $partNumber (${formattedDuration.padStart(6)}): $result")
}

private fun Duration.formatNicely(): String = when {
    inWholeSeconds > 0 -> "%.2fs".format(inWholeMilliseconds / 1000.0)
    else -> "%.2fms".format(inWholeNanoseconds / 1_000_000.0)
}