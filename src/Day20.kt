private object Day20 : Day(20) {
    private val file = input.lines().map(String::toInt)

    private fun List<Int>.decrypt(decryptKey: Int = 1, mixTimes: Int = 1): Long {
        val original = mapIndexed { index, i -> index to i.toLong() * decryptKey }
        val moved = original.toMutableList()

        repeat(mixTimes) {
            original.forEach { p ->
                val idx = moved.indexOf(p)

                moved.removeAt(idx)
                moved.add((idx + p.second).mod(moved.size), p)
            }
        }

        val mixed = moved.map(Pair<Int, Long>::second)

        fun getValue(index: Int) = mixed[(mixed.indexOf(0) + index) % mixed.size]

        return getValue(1000) + getValue(2000) + getValue(3000)
    }

    override fun part1() = file.decrypt()
    override fun part2() = file.decrypt(decryptKey = 811589153, mixTimes = 10)
}

private fun main() = Day20.main()