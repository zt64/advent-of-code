package util

enum class Direction(val offset: Point) {
    NORTH(0 to -1),
    EAST(1 to 0),
    SOUTH(0 to 1),
    WEST(-1 to 0)
}

fun Direction.turnRight(): Direction = when (this) {
    Direction.NORTH -> Direction.EAST
    Direction.EAST -> Direction.SOUTH
    Direction.SOUTH -> Direction.WEST
    Direction.WEST -> Direction.NORTH
}

fun Direction.turnLeft(): Direction = when (this) {
    Direction.NORTH -> Direction.WEST
    Direction.WEST -> Direction.SOUTH
    Direction.SOUTH -> Direction.EAST
    Direction.EAST -> Direction.NORTH
}