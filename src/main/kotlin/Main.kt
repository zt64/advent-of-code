
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
)

fun main(args: Array<String>) = runBlocking {
    val calendar = Calendar.getInstance()

    val dayIndex = if (calendar.get(Calendar.YEAR) == 2025 && args.isEmpty()) {
        calendar.get(Calendar.DAY_OF_MONTH)
    } else {
        if (args.isEmpty()) {
            println("Please provide a day number")
            return@runBlocking
        }

        args.first().toIntOrNull()
    }?.takeIf { i -> i in 1..days.size } ?: error("Invalid day")

    val dayClass = days.getOrNull(3) ?: error("Day not found")
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
        print("\r$message ${progressChars[index % progressChars.size]}\n")
        index++
        delay(80)
    }
}