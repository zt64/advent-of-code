package day

import util.product
import util.substringBetween

class Day10(input: String) : Day(input) {
    private data class Machine(
        val lights: Int,
        val schematics: List<Int>,
        val requirements: List<Int>
    )

    private val machines = input.lines().map { line ->
        val lights = line.substringBetween("[", "]")
            .foldIndexed(0) { i, acc, c -> if (c == '#') acc or (1 shl i) else acc }

        val schematics = line.split('(').drop(1).map {
            it.substringBefore(')')
                .split(',')
                .fold(0) { acc, i -> acc or (1 shl i.toInt()) }
        }

        val requirements = line.substringBetween("{", "}")
            .split(',')
            .map(String::toInt)

        Machine(lights, schematics, requirements)
    }

    override fun part1(): Any {
        return machines.sumOf { (lights, schematics) ->
            generateSequence(1, Int::inc).first { repeat ->
                schematics.product(repeat).any { buttons ->
                    buttons.fold(0, Int::xor) == lights
                }
            }
        }
    }

    override fun part2(): Any {
        return 0
    }
}