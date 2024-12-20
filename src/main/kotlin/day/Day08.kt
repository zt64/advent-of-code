package day

import util.*

object Day08 : Day(8) {
    private val grid = input.to2DArray<Char>()

    private val antennas = buildList {
        grid.forEach2D { ch, row, col ->
            if (ch == '.') return@forEach2D

            add(ch to Pair(row, col))
        }
    }

    override fun part1(): Any {
        return antennas.flatMap { (aFreq, a) ->
            antennas.mapNotNull { (bFreq, b) ->
                if (a == b || aFreq != bFreq) return@mapNotNull null

                (a + a - b).takeIf { it in grid }
            }
        }.distinct().size
    }

    override fun part2(): Any {
        val antiNodes = buildSet {
            antennas.forEach { (aFreq, a) ->
                antennas.forEach { (bFreq, b) ->
                    if (a == b || aFreq != bFreq) return@forEach

                    var antiNode = a + a - b

                    while (true) {
                        if (antiNode !in grid) break

                        add(antiNode)

                        antiNode = antiNode + a - b
                    }
                }
            }
        }.distinct()

        return (antiNodes + antennas.map { it.second }).distinct().size
    }
}