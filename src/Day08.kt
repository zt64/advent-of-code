@OptIn(ExperimentalStdlibApi::class)
private object Day8 : Day(8) {
    private val map = input.lines().map { line -> line.map(Char::digitToInt) }
    private val width = map.first().size
    private val height = map.size

    private fun isVisible(rowIndex: Int, columnIndex: Int): Boolean {
        if (rowIndex == 0 || columnIndex == 0 || rowIndex == width - 1 || columnIndex == height - 1) return true

        val treeHeight = map[rowIndex][columnIndex]
        val row = map[rowIndex]
        val col = getColumn(columnIndex)

        val left = row.slice(0..<columnIndex).max()
        val right = row.slice(columnIndex + 1..<height).max()
        val up = col.slice(0..<rowIndex).max()
        val down = col.slice(rowIndex + 1..<height).max()

        return treeHeight > left || treeHeight > right || treeHeight > up || treeHeight > down
    }

    private fun treeScore(rowIndex: Int, columnIndex: Int): Int {
        if (rowIndex == 0 || columnIndex == 0 || rowIndex == width - 1 || columnIndex == height - 1) return 0

        val treeHeight = map[rowIndex][columnIndex]
        val row = map[rowIndex]
        val col = getColumn(columnIndex)

        fun getScore(treeLine: List<Int>): Int {
            var count = 0

            for (t in treeLine) {
                count++
                if (t >= treeHeight) break
            }

            return count
        }

        val left = getScore(row.slice(0..<columnIndex).reversed())
        val right = getScore(row.slice(columnIndex + 1..<height))
        val up = getScore(col.slice(0..<rowIndex).reversed())
        val down = getScore(col.slice(rowIndex + 1..<height))

        return left * right * up * down
    }

    override fun part1(): Int = map.indices.sumOf { row ->
        map[row].indices.count { col ->
            isVisible(row, col)
        }
    }

    override fun part2(): Int = map.indices.maxOf { row ->
        map[row].indices.maxOf { col ->
            treeScore(row, col)
        }
    }

    private fun getColumn(index: Int) = map.map { it[index] }
}

fun main() = Day8.main()