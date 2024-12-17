package day

import util.pow
import util.substringBetween

object Day17 : Day(17) {
    private val rawProgram = input.substringAfter("Program: ").split(",").map { it.toInt() }

    private class Program(var ra: Long = 0, var rb: Long = 0, var rc: Long = 0) {
        private val instructions = rawProgram.chunked(2)
            .map { (opcode, operand) -> Instruction.entries[opcode] to operand }

        private fun getOperandValue(operand: Int): Long = when (operand) {
            in 0..3, 7 -> operand.toLong()
            4 -> ra
            5 -> rb
            6 -> rc
            else -> error("Invalid operand")
        }

        private fun dv(operand: Int): Long = (ra / 2.pow(getOperandValue(operand).toInt())).toLong()

        operator fun invoke(): List<Int> {
            var instructionPointer = 0

            return buildList {
                while (instructionPointer < instructions.size) {
                    val (opcode, operand) = instructions[instructionPointer]
                    when (opcode) {
                        Instruction.BXL -> rb = rb xor operand.toLong()
                        Instruction.BST -> rb = getOperandValue(operand).mod(8).toLong()
                        Instruction.BXC -> rb = rb xor rc
                        Instruction.ADV -> ra = dv(operand)
                        Instruction.BDV -> rb = dv(operand)
                        Instruction.CDV -> rc = dv(operand)
                        Instruction.OUT -> add(getOperandValue(operand).mod(8))
                        Instruction.JNZ -> if (ra != 0L) {
                            instructionPointer = operand
                            continue
                        }
                    }

                    instructionPointer++
                }
            }
        }

        private enum class Instruction {
            ADV, BXL, BST, JNZ, BXC, OUT, BDV, CDV
        }
    }

    override fun part1(): Any {
        val p = Program(
            ra = input.substringBetween("Register A: ", "\n").toLong(),
            rb = input.substringBetween("Register B: ", "\n").toLong(),
            rc = input.substringBetween("Register C: ", "\n").toLong()
        )

        return p()
    }

    override fun part2(): Any {
        var valid = listOf(0L)
        val rawProgramSuffixes = (1..16).map { rawProgram.takeLast(it) }
        val program = Program()

        for (i in 1..rawProgram.size) {
            val suffix = rawProgramSuffixes[i - 1]

            valid = buildList {
                valid.forEach { num ->
                    for (offset in 0..8) {
                        val a = (8L * num + offset).toLong()
                        program.ra = a

                        if (program() == suffix) add(a)
                    }
                }
            }
        }

        return valid.min()
    }
}