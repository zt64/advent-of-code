
import day.*
import kotlinx.coroutines.*
import java.util.*
import kotlin.time.Duration
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
    Day11::class,
    Day12::class,
    Day13::class,
    Day14::class,
    Day15::class,
    Day16::class,
    Day17::class,
    Day18::class,
    Day19::class,
    Day20::class,
    Day21::class,
    Day22::class,
    Day23::class,
    Day24::class,
    Day25::class
)

fun main(args: Array<String>) = runBlocking {
    val calendar = Calendar.getInstance()

    val dayIndex = if (calendar.get(Calendar.YEAR) == 2024 && args.isEmpty()) {
        calendar.get(Calendar.DAY_OF_MONTH)
    } else {
        if (args.isEmpty()) {
            println("Please provide a day number")
            return@runBlocking
        }

        args.first().toIntOrNull()
    }?.takeIf { i -> i in 1..days.size } ?: error("Invalid day")

    val dayClass = days.getOrNull(dayIndex - 1) ?: error("Day not found")
    val day = dayClass.objectInstance!!

    println("\n🎄 Day ${dayIndex.toString().padStart(2, '0')} 🎄")
    println("═".repeat(30))

    withContext(Dispatchers.IO) {
        runPart(1, day::part1)
        runPart(2, day::part2)
    }
}

private suspend fun CoroutineScope.runPart(partNumber: Int, solve: () -> Any) {
    launch {
        val progressJob = launchProgressIndicator("Running part $partNumber")
        val (result, duration) = measureTimedValue(solve)
        progressJob.cancel()

        val formattedDuration = duration.formatNicely()
        println("\r* Part $partNumber (${formattedDuration.padStart(6)}): $result")
    }.join()
}

private fun Duration.formatNicely(): String = when {
    inWholeSeconds > 0 -> "%.2fs".format(inWholeMilliseconds / 1000.0)
    else -> "%.2fms".format(inWholeNanoseconds / 1_000_000.0)
}

private fun CoroutineScope.launchProgressIndicator(message: String) = launch {
    val progressChars = "⠋⠙⠹⠸⠼⠴⠦⠧⠇⠏".toList()
    var index = 0
    while (isActive) {
        print("\r$message ${progressChars[index % progressChars.size]}")
        index++
        delay(80)
    }
}