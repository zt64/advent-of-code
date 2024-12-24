package day

object Day22 : Day(22) {
    private val initialSecrets = input.lines().map { it.toInt() }

    private fun calculateSecretNumber(num: Long): Long {
        var a = num
        fun Long.mixAndPrune() = (this xor a).mod(16777216L)
        a = (num * 64).mixAndPrune()
        a = (a / 32).mixAndPrune()
        a = (a * 2048).mixAndPrune()
        return a
    }

    override fun part1(): Any {
        return initialSecrets.sumOf {
            var a = it.toLong()
            repeat(2000) {
                a = calculateSecretNumber(a)
            }
            a
        }
    }

    override fun part2(): Any {
        val prices = HashMap<List<Long>, Long>().withDefault { 0 }

        initialSecrets.forEach { n ->
            val numbers = buildList {
                var a = n.toLong()
                add(a % 10)
                repeat(1999) {
                    a = calculateSecretNumber(a)
                    add(a % 10)
                }
            }

            val seen = mutableSetOf<List<Long>>()
            val diffs = numbers.zipWithNext { a, b -> b - a }

            diffs.windowed(4).forEachIndexed { i, seq ->
                if (seen.add(seq)) prices[seq] = prices.getValue(seq) + numbers[i + 4]
            }
        }

        return prices.maxOf { it.value }
    }
}