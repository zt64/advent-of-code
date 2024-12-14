package day

private typealias Game = Triple<Pair<Long, Long>, Pair<Long, Long>, Pair<Long, Long>>

object Day13 : Day(13) {
    private val games = input.split("\n\n").map {
        val pairs = it.lines().map {
            it.substringAfter(": ")
                .split(", ")
                .map { it.drop(2).toLong() }.let { it[0] to it[1] }
        }

        Triple(pairs[0], pairs[1], pairs[2])
    }

    override fun part1(): Any = games.countTokens()

    override fun part2(): Any {
        return games.map { game ->
            game.copy(
                third = game.third.let {
                    it.first + 10000000000000 to it.second + 10000000000000
                }
            )
        }.countTokens()
    }

    // dont ask me how this works, cause i sure as hell dont know
    private fun List<Game>.countTokens(): Long {
        return sumOf { (buttonA, buttonB, prize) ->
            val (ax, ay) = buttonA
            val (bx, by) = buttonB
            val (px, py) = prize

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
}