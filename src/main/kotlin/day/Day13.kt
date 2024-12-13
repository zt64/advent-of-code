package day

object Day13 : Day(13) {
    private val games = input.split("\n\n").map {
        val pairs = it.lines().map {
            it.substringAfter(": ")
                .split(", ")
                .map { it.drop(2).toLong() }.let { it[0] to it[1] }
        }

        Game(pairs[0], pairs[1], pairs[2])
    }

    private data class Game(
        val buttonA: Pair<Long, Long>,
        val buttonB: Pair<Long, Long>,
        val prize: Pair<Long, Long>
    )

    override fun part1(): Any = games.countTokens()

    override fun part2(): Any {
        return games.map { game ->
            game.copy(
                prize = game.prize.let {
                    it.first + 10000000000000 to it.second + 10000000000000
                }
            )
        }.countTokens()
    }

    // dont ask me how this works, cause i sure as hell dont know
    private fun List<Game>.countTokens(): Long {
        return sumOf { game ->
            val ax = game.buttonA.x
            val ay = game.buttonA.y
            val bx = game.buttonB.x
            val by = game.buttonB.y
            val px = game.prize.x
            val py = game.prize.y

            val a = (by * px) + (-bx * py)
            // common denominator
            val c = ax * by - ay * bx
            // ensure that the button press is a whole number
            if (a % c != 0L) return@sumOf 0

            val b = (-ay * px) + (ax * py)
            // ensure that the button press is a whole number
            if (b % c != 0L) return@sumOf 0

            val buttonAPress = a / c
            val buttonBPress = b / c

            buttonAPress * 3 + buttonBPress * 1
        }
    }

    private val Pair<Long, Long>.x get() = first
    private val Pair<Long, Long>.y get() = second
}