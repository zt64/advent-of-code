package day

import util.contains
import util.forEach2D
import util.to2DArray

object Day08 : Day(8) {
    private val grid = input.to2DArray<Char>()

    private val antennas = buildList {
        grid.forEach2D { ch, row, col ->
            if (ch == '.') return@forEach2D

            add(ch to Pair(row, col))
        }
    }

    override fun part1(): Any {
        return antennas.flatMap { (aFreq, a)->
            antennas.mapNotNull { (bFreq, b) ->
                if (a == b || aFreq != bFreq) return@mapNotNull null

                Pair(a.x + a.x - b.x, a.y + a.y - b.y).takeUnless {
                    it !in grid
                }
            }
        }.distinct().size
    }

    override fun part2(): Any {
        val antiNodes = buildSet {
            antennas.forEach { (aFreq, a)->
                antennas.forEach { (bFreq, b) ->
                    if (a == b || aFreq != bFreq) return@forEach

                    var antiNode = Pair(a.x + a.x - b.x, a.y + a.y - b.y)

                    while (true) {
                        if (antiNode !in grid) break

                        add(antiNode)

                        antiNode = Pair(antiNode.x + a.x - b.x, antiNode.y + a.y - b.y)
                    }
                }
            }
        }.distinct()

        return (antiNodes + antennas.map { it.second }).distinct().size
    }

    private val Pair<Int, Int>.x get() = first
    private val Pair<Int, Int>.y get() = second
}