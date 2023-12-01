enum class Shape(val score: Int) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3)
}

enum class Result {
    WIN,
    LOSE,
    DRAW
}

fun main() {
    val input = readInput("Day02")

    val mappings = mapOf(
        "A" to Shape.ROCK,
        "B" to Shape.PAPER,
        "C" to Shape.SCISSORS,
        "X" to Shape.ROCK,
        "Y" to Shape.PAPER,
        "Z" to Shape.SCISSORS
    )

    fun part1(input: String): Int {
        var score = 0

        input.lines().forEach { line ->
            val (attack, response) = line.split(" ")
                .zipWithNext { a, b -> mappings[a]!! to mappings[b]!! }
                .single()

            score += response.score + when (response to attack) {
                Shape.ROCK to Shape.SCISSORS,
                Shape.PAPER to Shape.ROCK,
                Shape.SCISSORS to Shape.PAPER -> 6

                else -> if (response == attack) 3 else 0
            }
        }

        return score
    }

    val result = mapOf(
        "Z" to Result.WIN,
        "X" to Result.LOSE,
        "Y" to Result.DRAW
    )

    fun part2(input: String): Int {
        var score = 0

        input.lines().forEach { line ->
            val (attack, end) = line.split(" ")
                .zipWithNext { a, b -> mappings[a]!! to result[b]!! }
                .single()

            val response = when (end) {
                Result.WIN -> when (attack) {
                    Shape.ROCK -> Shape.PAPER
                    Shape.PAPER -> Shape.SCISSORS
                    Shape.SCISSORS -> Shape.ROCK
                }
                Result.LOSE -> when (attack) {
                    Shape.ROCK -> Shape.SCISSORS
                    Shape.PAPER -> Shape.ROCK
                    Shape.SCISSORS -> Shape.PAPER
                }
                Result.DRAW -> attack
            }

            score += response.score + when (response to attack) {
                Shape.ROCK to Shape.SCISSORS,
                Shape.PAPER to Shape.ROCK,
                Shape.SCISSORS to Shape.PAPER -> 6

                else -> if (response == attack) 3 else 0
            }
        }

        return score
    }

    println(part1(input))
    println(part2(input))
}