package day

class Day05(input: String) : Day(input) {
    private val idRanges = input.substringBefore("\n\n").lines().map {
        it.split("-").let { it[0].toLong()..it[1].toLong() }
    }.sortedBy { it.first }

    private val available = input.substringAfter("\n\n").lines().map { it.toLong() }

    override fun part1(): Any {
        return available.count { ingredientId ->
            idRanges.any { range -> ingredientId in range }
        }
    }

    override fun part2(): Any {
        val newRanges = mutableListOf<LongRange>()

        newRanges += idRanges.first()
        // idRanges.drop(1).forEach { range ->
        //     for ((i, outerRange) in newRanges.toList().withIndex()) {
        //         when {
        //             outerRange.last < range.last && outerRange.last > range.first -> {
        //
        //                 newRanges[i] = range.first..outerRange.last
        //             }
        //             outerRange.first > range.first && outerRange.first < range.last -> {
        //                 newRanges[i] = outerRange.first..range.last
        //             }
        //             outerRange.first >= range.first && range.last <= outerRange.last -> {
        //                 continue
        //             }
        //             else -> {
        //                 newRanges += range
        //             }
        //         }
        //     }
        // }

        return newRanges
    }
}