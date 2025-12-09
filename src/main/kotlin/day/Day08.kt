package day

import util.Point3D

class Day08(input: String) : Day(input) {
    private val boxes = input.lines().map {
        it.split(",").let { Point3D(it[0].toInt(), it[1].toInt(), it[2].toInt()) }
    }

    private fun closestPair(points: List<Point3D>): Pair<Point3D, Point3D>? {
        var bestDist = Double.POSITIVE_INFINITY
        var bestPair: Pair<Point3D, Point3D>? = null

        for (i in points.indices) {
            for (j in i + 1 until points.size) {
                val dist = points[i].dist(points[j])

                if (dist < bestDist) {
                    bestDist = dist
                    bestPair = points[i] to points[j]
                }
            }
        }

        return bestPair
    }

    override fun part1(): Any {
        val circuits = ArrayList<MutableSet<Point3D>>()

        val dists = boxes.flatMapIndexed { i, a ->
            buildList {
                for (j in i + 1 until boxes.size) {
                    val dist = a.distSqr(boxes[j])


                    add(Triple(dist, i, j))
                }
            }
        }
        val sorted = dists.sortedWith(compareBy(Triple<Int,Int,Int>::first,
            Triple<Int,Int,Int>::second,
            Triple<Int,Int,Int>::third))

        println(sorted)

        circuits.addAll(boxes.map { mutableSetOf(it) })
        repeat(10) {

            var bestDist = Double.POSITIVE_INFINITY
            var bestPair: Pair<Point3D, Point3D>? = null

            for ((i, a) in boxes.withIndex()) {
                for (j in i + 1 until boxes.size) {
                    val b = boxes[j]

                    if (circuits.any { a in it } && circuits.any { b in it }) {
                        val contain = circuits.indexOfFirst { a in it }
                        val contain2 = circuits.indexOfFirst { b in it }
                        if (contain != contain2) {

                            circuits[contain].addAll(circuits[contain2])
                            circuits.removeAt(contain2)
                            continue
                        }

                    }

                    val dist = a.dist(boxes[j])

                    if (dist < bestDist) {
                        bestDist = dist
                        bestPair = boxes[i] to boxes[j]
                    }
                }
            }
            //
            // val (a, b) = bestPair!!
            //
            // circuits += mutableSetOf(a, b)
        }
        //
        // val pairs = ArrayDeque<Pair<Point3D, Point3D>>()
        // pairs += closestPair(boxes)!!

        // while (pairs.isNotEmpty()) {
        //     val (a, b) = pairs.removeFirst()
        //
        //     val existingCircuit = circuits.find { a in it || b in it }
        //     if (existingCircuit != null) {
        //         existingCircuit += setOf(a, b)
        //     } else {
        //         circuits += mutableSetOf(a, b)
        //     }
        //
        //     var bestDist = Double.POSITIVE_INFINITY
        //     var bestPair: Pair<Point3D, Point3D>? = null
        //
        //     for ((i, a) in boxes.withIndex()) {
        //         for (j in i + 1 until boxes.size) {
        //             if (circuits.any { a in it && b in it }) continue
        //
        //             val dist = a.dist(boxes[j])
        //
        //             if (dist < bestDist) {
        //                 bestDist = dist
        //                 bestPair = boxes[i] to boxes[j]
        //             }
        //         }
        //     }
        //
        //     pairs += bestPair ?: break
        //
        //     println("$a $b")
        // }
        //
        // repeat(1000) {
        //
        // }
        println(circuits.joinToString("\n"))
        return circuits.sortedBy { it.size }.take(3).fold(1) { acc, boxes ->
            acc * boxes.size
        }
    }

    override fun part2(): Any {
        return 0
    }
}