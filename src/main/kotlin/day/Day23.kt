package day

object Day23 : Day(23) {
    private val graph = buildMap {
        input.lines().map {
            val (n1, n2) = it.split("-").let { it[0] to it[1] }

            this[n1] = getOrDefault(n1, emptySet()) + n2
            this[n2] = getOrDefault(n2, emptySet()) + n1
        }
    }

    override fun part1(): Any {
        return bronKerbosch(graph.keys.toMutableSet())
            .filter { it.size >= 3 }
            .flatMap { clique ->
                clique.toList().combinations(3).map { it.toSet() }
            }
            .toSet()
            .count { it.any { it.startsWith('t') } }
    }

    private fun <T> List<T>.combinations(k: Int): List<List<T>> {
        if (k == 0) return listOf(emptyList())
        if (k > size) return emptyList()
        return drop(1).combinations(k - 1).map { listOf(first()) + it } + drop(1).combinations(k)
    }

    override fun part2(): Any {
        return bronKerbosch(graph.keys.toMutableSet()).maxBy { it.size }.sorted().joinToString(",")
    }

    private fun bronKerbosch(
        p: MutableSet<String>,
        r: Set<String> = emptySet(),
        x: MutableSet<String> = mutableSetOf(),
    ): Set<Set<String>> {
        if (p.isEmpty() && x.isEmpty()) return setOf(r)

        return p.toList().flatMap { vertex ->
            bronKerbosch(
                r = r + vertex,
                p = p.intersect(graph[vertex]!!).toMutableSet(),
                x = x.intersect(graph[vertex]!!).toMutableSet()
            ).also {
                p -= vertex
                x += vertex
            }
        }.toSet()
    }
}