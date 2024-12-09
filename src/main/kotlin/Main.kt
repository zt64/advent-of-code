
import day.*
import kotlinx.coroutines.*
import java.util.*
import kotlin.time.measureTimedValue

private val days = listOf<Day>(
    Day01,
    Day02,
    Day03,
    Day04,
    Day05,
    Day06,
    Day07,
    Day08,
    Day09,
    // Day10,
    // Day11,
    // Day12,
    // Day13,
    // Day14,
    // Day15,
    // Day16,
    // Day17,
    // Day18,
    // Day19,
    // Day20,
    // Day21,
    // Day22,
    // Day23,
    // Day24,
    // Day25
)

fun main(args: Array<String>) {
    val calendar = Calendar.getInstance()

    val dayIndex = if (calendar.get(Calendar.YEAR) == 2024) {
        calendar.get(Calendar.DAY_OF_MONTH)
    } else {
        args.firstOrNull()?.toIntOrNull()
    }?.takeIf { i ->
        i in 1..days.size
    } ?: error("Invalid day")

    val day = days.getOrNull(dayIndex - 1) ?: error("day.Day not found")

    println("Day ${day.number}")

    runBlocking(Dispatchers.IO) {
        val part1Job = launch {
            val progressJob = launchProgressIndicator("Processing part 1")
            val (part1, part1Duration) = measureTimedValue(day::part1)
            progressJob.cancel()
            println("\r* Part 1 (took ${part1Duration}): $part1")
        }
        part1Job.join()

        val part2Job = launch {
            val progressJob = launchProgressIndicator("Processing part 2")
            val (part2, part2Duration) = measureTimedValue(day::part2)
            progressJob.cancel()
            println("\r* Part 2 (took ${part2Duration}): $part2")
        }
        part2Job.join()
    }
}

private fun CoroutineScope.launchProgressIndicator(message: String) = launch {
    val progressChars = arrayOf("|", "/", "-", "\\")
    var index = 0
    while (isActive) {
        print("\r$message ${progressChars[index % progressChars.size]}")
        index++
        delay(100)
    }
}