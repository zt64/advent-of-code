package day

object Day11 : Day(11) {
    private val stones = input.split(" ").map { it.toLong() }
    private val cache = mutableMapOf<Pair<Int, Long>, Long>()

    private fun count(stone: Long, limit: Int, blink: Int = 0): Long {
        if (blink == limit) return 1

        cache[blink to stone]?.let { return it }

        cache[blink to stone] = when {
            stone == 0L -> count(1, limit, blink + 1)
            stone.toString().length % 2 == 0 -> {
                val (first, second) = stone.toString().let {
                    it.take(it.length / 2).toLong() to it.drop(it.length / 2).toLong()
                }

                count(first, limit, blink + 1) + count(second, limit,blink + 1)
            }
            else -> count(stone * 2024, limit,blink + 1)
        }

        return cache[blink to stone]!!
    }

    private fun calculate(blinks: Int): Long {
        cache.clear()
        return stones.sumOf { count(it, blinks) }
    }

    override fun part1(): Any = calculate(25)

    override fun part2(): Any = calculate(75)
}