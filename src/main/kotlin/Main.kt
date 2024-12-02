
import kotlinx.coroutines.*
import kotlin.time.measureTimedValue

fun main(args: Array<String>) {
    val day = args.firstOrNull()?.toIntOrNull()?.let { TODO() } ?: Day02

    printSolution(day)
}

fun printSolution(day: Day) {
    println("Day ${day.number}")

    runBlocking {
        val part1Job = launch {
            val progressJob = launchProgressIndicator("Processing Part 1")
            val (part1, part1Duration) = measureTimedValue { day.part1() }
            progressJob.cancel()
            println("\rPart 1: (took $part1Duration) $part1")
        }
        part1Job.join()

        val part2Job = launch {
            val progressJob = launchProgressIndicator("Processing Part 2")
            val (part2, part2Duration) = measureTimedValue { day.part2() }
            progressJob.cancel()
            println("\rPart 2: (took $part2Duration) $part2")
        }
        part2Job.join()
    }
}

fun CoroutineScope.launchProgressIndicator(message: String) = launch {
    val progressChars = arrayOf("|", "/", "-", "\\")
    var index = 0
    while (isActive) {
        print("\r$message ${progressChars[index % progressChars.size]}")
        index++
        delay(100)
    }
}