package day

import java.util.*

object Day24 : Day(24) {
    private val regex = "(.{3}) (.*?) (.{3}) -> (.{3})".toRegex()

    private enum class Operation {
        AND, XOR, OR
    }

    private data class Gate(val a: String, val b: String, var out: String, val op: Operation)

    private val initialWires = input.substringBefore("\n\n").lines().associate {
        it.split(": ").let { it[0] to it[1].toInt() }
    }.toMutableMap()

    private val gates = input.substringAfter("\n\n").lines().map {
        regex.matchEntire(it)!!.destructured.let { (a, gate, b, output) ->
            Gate(
                a, b, output, when (gate) {
                    "AND" -> Operation.AND
                    "XOR" -> Operation.XOR
                    "OR" -> Operation.OR
                    else -> error("Invalid gate type")
                }
            )
        }
    }.toMutableList()

    override fun part1() = calculateValue(initialWires)

    override fun part2(): Any {
        val expected = initialWires.filterKeys { it.startsWith('x') }.values
            .joinToString("").reversed().toLong(2) + initialWires.filterKeys { it.startsWith('y') }.values
            .joinToString("").reversed().toLong(2)

        val nxz = gates.filter { it.out.startsWith("z") && it.op != Operation.XOR }.drop(1)
        val xnz = gates.filter { it.a[0] !in "xy" && it.b[0] !in "xy" && it.out[0] != 'z' && it.op == Operation.XOR }

        xnz.forEach { i ->
            val b = nxz.first { it.out == gates.findFirstZThatUses(i.out) }
            val temp = i.out
            i.out = b.out
            b.out = temp
        }

        val n = (expected xor calculateValue(initialWires)).countTrailingZeroBits().toString()

        return (xnz + nxz + gates.filter { it.a.endsWith(n) && it.b.endsWith(n) }).map { it.out }.sorted().joinToString(",")
    }

    private fun calculateValue(wires: Map<String, Int>): Long {
        val wires = wires.toMutableMap()
        val toCheck = ArrayDeque<Gate>().apply { addAll(gates) }

        while (toCheck.isNotEmpty()) {
            val gate = toCheck.pop()

            if (gate.a !in wires || gate.b !in wires) {
                toCheck.add(gate)
                continue
            }

            val aVal = wires[gate.a]!!
            val bVal = wires[gate.b]!!

            wires[gate.out] = when (gate.op) {
                Operation.AND -> aVal and bVal
                Operation.XOR -> aVal xor bVal
                Operation.OR -> aVal or bVal
            }
        }

        return wires
            .filterKeys { it.startsWith('z') }
            .toSortedMap { a, b -> b.drop(1).toInt() - a.drop(1).toInt() }
            .values
            .joinToString("")
            .toLong(2)
    }

    private fun List<Gate>.findFirstZThatUses(c: String): String? {
        val x = filter { it.a == c || it.b == c }
        x.find { it.out.startsWith('z') }?.let {
            return "z" + (it.out.drop(1).toInt() - 1).toString().padStart(2, '0')
        }
        return x.firstNotNullOfOrNull { findFirstZThatUses(it.out) }
    }
}