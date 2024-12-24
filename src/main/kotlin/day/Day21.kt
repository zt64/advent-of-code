package day

import util.*

object Day21 : Day(21) {
    private val codes = input.lines()

    override fun part1(): Int = codes.sumOf { code ->
        val ways1 = ways(code, numpad)
        val ways2 = mutableListOf<String>()
        for (way in ways1) {
            ways2.addAll(ways(way, dpad))
        }
        val ways3 = mutableListOf<String>()
        for (way in ways2) {
            ways3.addAll(ways(way, dpad))
        }

        val shortest = ways3.minOf { it.length }

        code.take(3).toInt() * shortest
    }

    override fun part2(): Long {
        return codes.sumOf {
            getCodeCost(it, 25) * it.take(3).toInt()
        }
    }

    private val numpad = mapOf(
        '7' to (0 to 0),
        '8' to (0 to 1),
        '9' to (0 to 2),
        '4' to (1 to 0),
        '5' to (1 to 1),
        '6' to (1 to 2),
        '1' to (2 to 0),
        '2' to (2 to 1),
        '3' to (2 to 2),
        '0' to (3 to 1),
        'A' to (3 to 2)
    )

    private val dpad = mapOf(
        '^' to (0 to 1),
        'A' to (0 to 2),
        '<' to (1 to 0),
        'v' to (1 to 1),
        '>' to (1 to 2)
    )

    private val directions = mapOf(
        '>' to Directions.East,
        'v' to Directions.South,
        '<' to Directions.West,
        '^' to Directions.North
    )

    private fun ways(code: String, keypad: Map<Char, Point>): List<String> {
        val parts = mutableListOf<List<String>>()
        var curLoc = keypad['A']!!

        code.forEach { c ->
            val nextLoc = keypad[c]!!
            val (di, dj) = nextLoc - curLoc

            val moves = buildString {
                if (di > 0) {
                    append("v".repeat(di))
                } else if (di < 0) {
                    append("^".repeat(-di))
                }
                if (dj > 0) {
                    append(">".repeat(dj))
                } else if (dj < 0) {
                    append("<".repeat(-dj))
                }
            }

            val combos = if (moves.isEmpty()) {
                // If we're already at the target position, just press A
                listOf("A")
            } else {
                moves
                    .toList()
                    .permutations()
                    .map { it.joinToString("") + "A" }
                    .distinct()
                    .filter { combo ->
                        var next = curLoc
                        var good = true
                        for (ch in combo.dropLast(1)) {
                            val dc = directions[ch]!!
                            next += dc
                            if (next !in keypad.values) {
                                good = false
                                break
                            }
                        }
                        good
                    }
            }

            parts += combos
            curLoc = nextLoc
        }

        return parts.cartesianProduct().map { it.joinToString("") }
    }

    private val waysCache = mutableMapOf<Triple<Char, Char, Boolean>, List<String>>()
    private fun generateWays(a: Char, b: Char, keypad: Boolean): List<String> {
        return waysCache.getOrPut(Triple(a, b, keypad)) {
            val pad = if (keypad) dpad else numpad
            val curLoc = pad[a]!!
            val nextLoc = pad[b]!!
            val (di, dj) = nextLoc - curLoc

            val moves = buildList {
                if (di > 0) {
                    add("v")
                    add(di)
                } else {
                    add("^")
                    add(-di)
                }
                if (dj > 0) {
                    add(">")
                    add(dj)
                } else {
                    add("<")
                    add(-dj)
                }
            }

            val rawCombos = getCombos(
                moves[0] as String, moves[1] as Int,
                moves[2] as String, moves[3] as Int
            ).map { it + "A" }.distinct().toList()

            rawCombos.filter { combo ->
                var cur = curLoc
                combo.dropLast(1).all { c ->
                    val d = directions[c] ?: error("Invalid direction: $c")
                    cur += d
                    cur in pad.values
                }
            }
        }
    }

    private val costCache = mutableMapOf<String, Long>()
    private fun getCost(a: Char, b: Char, keypad: Boolean, depth: Int = 0): Long {
        return costCache.getOrPut("$a$b$keypad$depth") {
            if (depth == 0) {
                check(keypad)
                return generateWays(a, b, true).minOf { it.length }.toLong()
            }

            val ways = generateWays(a, b, keypad)
            var bestCost = Long.MAX_VALUE

            ways.forEach { seq ->
                val fullSeq = "A$seq"
                var cost = 0L
                for (i in 0 until fullSeq.length - 1) {
                    val (from, to) = fullSeq[i] to fullSeq[i + 1]
                    cost += getCost(from, to, true, depth - 1)
                }
                bestCost = minOf(bestCost, cost)
            }

            bestCost
        }
    }

    private fun getCodeCost(code: String, depth: Int): Long {
        val fullCode = "A$code"
        var cost = 0L
        for (i in 0 until fullCode.length - 1) {
            val (from, to) = fullCode[i] to fullCode[i + 1]
            cost += getCost(from, to, false, depth)
        }
        return cost
    }

    private fun getCombos(ca: String, a: Int, cb: String, b: Int): Sequence<String> = sequence {
        (0 until (a + b)).toList().combinations(a).forEach { idxs ->
            val res = Array(a + b) { cb }
            idxs.forEach { res[it] = ca }
            yield(res.joinToString(""))
        }
    }
}