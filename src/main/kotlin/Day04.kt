object Day04 : Day(4) {
    private const val MATCH = "XMAS"
    private const val MAGIC = 3
    val grid = input.lines().map { it.toCharArray() }
    override fun part1(): Any {
        var matches = 0
        grid.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { charIndex, c ->
                if (c != 'X') return@forEachIndexed

                fun check(xIncr: Int, yIncr: Int) {
                    var match = true

                    for (i in 1 until MATCH.length) {
                        if (grid[rowIndex + i * yIncr][charIndex + i * xIncr] != MATCH[i]) {
                            match = false
                            break
                        }
                    }

                    if (match) matches++
                }

                val canGoLeft = charIndex - MAGIC >= 0
                val canGoRight = charIndex + MAGIC < row.size
                val canGoUp = rowIndex - MAGIC >= 0
                val canGoDown = rowIndex + MAGIC < grid.size

                // Check above
                if (canGoUp) {
                    check(0, -1)

                    if (canGoLeft) check(-1, -1)
                    if (canGoRight) check(1, -1)
                }

                // Check below
                if (canGoDown) {
                    check(0, 1)

                    if (canGoLeft) check(-1, 1)
                    if (canGoRight) check(1, 1)
                }

                // Check left
                if (canGoLeft) check(-1, 0)

                // Check right
                if (canGoRight) check(1, 0)
            }
        }

        return matches
    }

    override fun part2(): Any {
        var matches = 0
        grid.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { charIndex, c ->
                if (c != 'A') return@forEachIndexed

                if (rowIndex == grid.size - 1 || charIndex == row.size - 1 ||
                    rowIndex == 0 || charIndex == 0
                ) return@forEachIndexed

                listOf(
                    "M.S\n...\nM.S",
                    "S.M\n...\nS.M",
                    "M.M\n...\nS.S",
                    "S.S\n...\nM.M"
                ).map {
                    it.split("\n").map { it.toCharArray() }
                }.forEach { pattern ->
                    var match = true

                    pattern.forEachIndexed { patternRowIndex, patternRow ->
                        patternRow.forEachIndexed { patternCharIndex, patternChar ->
                            if (patternChar == '.' || patternRowIndex == 1) return@forEachIndexed

                            if (grid[rowIndex + patternRowIndex - 1][charIndex + patternCharIndex - 1] != patternChar) {
                                match = false
                                return@forEachIndexed
                            }
                        }
                    }

                    if (match) matches++
                }
            }
        }

        return matches
    }
}