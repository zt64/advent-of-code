
import day.*
import kotlinx.coroutines.*
import java.util.*
import kotlin.time.measureTimedValue

private val days = listOf(
    Day01::class,
    Day02::class,
    Day03::class,
    Day04::class,
    Day05::class,
    Day06::class,
    Day07::class,
    Day08::class,
    Day09::class,
    Day10::class,
    // Day11::class,
    // Day12::class,
    // Day13::class,
    // Day14::class,
    // Day15::class,
    // Day16::class,
    // Day17::class,
    // Day18::class,
    // Day19::class,
    // Day20::class,
    // Day21::class,
    // Day22::class,
    // Day23::class,
    // Day24::class,
    // Day25::class
)

fun main(args: Array<String>) {
    val calendar = Calendar.getInstance()

    val dayIndex = if (calendar.get(Calendar.YEAR) == 2024) {
        calendar.get(Calendar.DAY_OF_MONTH)
    } else {
        args.firstOrNull()?.toIntOrNull()
    }?.takeIf { i -> i in 1..days.size } ?: error("Invalid day")

    val dayClass = days.getOrNull(dayIndex - 1) ?: error("Day not found")
    val day = dayClass.objectInstance!!

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