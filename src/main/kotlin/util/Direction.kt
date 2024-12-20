package util

// credit: https://github.com/wingio/advent-of-code/blob/2024/src/main/kotlin/util/grid/Point.kt

typealias Direction = Pair<Int, Int>

object Directions {
    val North = -1 to 0
    val NorthEast = -1 to 1
    val East = 0 to 1
    val SouthEast = 1 to 1
    val South = 1 to 0
    val SouthWest = 1 to -1
    val West = 0 to -1
    val NorthWest = -1 to -1

    val ALL = listOf(North, NorthWest, West, SouthWest, South, SouthEast, East, NorthEast)
    val CARDINALS = listOf(North, East, South, West)
    val WINDS = listOf(NorthWest, SouthWest, SouthEast, NorthEast)
}

fun Direction.turn(cw: Boolean): Direction {
    return Directions.ALL[(Directions.ALL.indexOf(this) + if (cw) 1 else -1).mod(Directions.ALL.size)]
}

fun Direction.turnCW(): Direction = turn(true)
fun Direction.turnCCW(): Direction = turn(false)

/**
 * Get the next cardinal direction by turning clockwise or counter-clockwise
 * according to [cw]
 *
 * @param cw Whether to turn clockwise, if false will turn counter-clockwise
 * @return The next cardinal direction
 */
fun Direction.cardinalTurn(cw: Boolean): Direction {
    return Directions.CARDINALS[(Directions.CARDINALS.indexOf(this) + if (cw) 1 else -1).mod(Directions.CARDINALS.size)]
}

fun Direction.cardinalTurnCW(): Direction = cardinalTurn(true)
fun Direction.cardinalTurnCCW(): Direction = cardinalTurn(false)