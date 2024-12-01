import kotlin.concurrent.thread
import kotlin.time.measureTimedValue

fun main() {
    val day = Day01

    printSolution(day)
}

fun printSolution(day: Day) {
    println("Day ${day.number}")

    val (part1, part1Duration) = measureTimedValue(day::part1)
    println("Part 1: (took $part1Duration) $part1")

    val (part2, part2Duration) = measureTimedValue(day::part2)
    println("Part 2: (took $part2Duration) $part2")
}

fun printProgressIndicator(message: String): Thread {
    val progressChars = arrayOf("|", "/", "-", "\\")
    var index = 0
    val progressThread = thread {
        while (!Thread.currentThread().isInterrupted) {
            print("\r$message ${progressChars[index % progressChars.size]}")
            index++
            Thread.sleep(100)
        }
    }
    Runtime.getRuntime().addShutdownHook(Thread(progressThread::interrupt))
    return progressThread
}