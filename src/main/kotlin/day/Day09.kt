package day

import java.math.BigInteger

object Day09 : Day(9) {
    private data class File(
        val id: Int,
        var free: Int,
        var size: Int,
        var blocks: MutableList<Int>
    )

    override fun part1(): Any {
        val blocks = input.windowed(2, 2, partialWindows = true).flatMapIndexed { id, string ->
            val size = string[0].toString().toInt()
            val free = string.getOrNull(1)?.toString()?.toInt() ?: 0

            List(size) { id } + List(free) { -1 }
        }.toMutableList()

        var j = 0

        for (i in blocks.size - 1 downTo 0) {
            if (blocks.subList(j, i).none { it == -1 }) break

            blocks.indexOfFirst { it == -1 }.apply {
                blocks[this] = blocks[i]
                blocks[i] = -1
                j = this
            }
        }

        return blocks.calculateChecksum()
    }

    override fun part2(): Any {
        val files = input.windowed(2, 2, partialWindows = true).mapIndexed { id, string ->
            val size = string[0].toString().toInt()
            val free = string.getOrNull(1)?.toString()?.toInt() ?: 0

            File(id, free, size,(List(size) { id } + List(free) { -1 }).toMutableList())
        }

        val tmp = files.toTypedArray()

        for (file in files.reversed()) {
            val usedBlocks = file.blocks.takeWhile { it != -1 }.take(file.size)

            val destFile = files.firstOrNull { (idy, free) ->
                idy < file.id && free >= usedBlocks.size
            } ?: continue

            val destBlocks = destFile.blocks
            val blockOffset = destBlocks.indexOfFirst { it == -1 }

            usedBlocks.forEachIndexed { i, block ->
                destBlocks[blockOffset + i] = block
                file.blocks[i] = -1
                destFile.free--
                file.free++
            }
        }

        return tmp.map { file -> file.blocks }.flatten().calculateChecksum()
    }

    private fun List<Int>.calculateChecksum(): BigInteger {
        var checksum = BigInteger.ZERO

        forEachIndexed { index, c ->
            if (c == -1) return@forEachIndexed

            checksum += index.toBigInteger() * c.toBigInteger()
        }

        return checksum
    }
}