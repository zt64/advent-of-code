import java.io.File
import java.util.*

fun main() {
    val (a, b) = File("./input/d19.txt").readText().split("\n\n")

    val workflows =
            a.split("\n").associate {
                val id = it.substringBefore("{")
                val rules =
                        it.substringAfter("{").substringBefore("}").split(",").map { rule ->
                            if (rule.contains(":")) {
                                val (a, b, res) = rule.split("<", ">", ":")
                                val right = b.toInt()

                                val result =
                                        when (res) {
                                            "A" -> Rule.Result.Accept
                                            "R" -> Rule.Result.Reject
                                            else -> Rule.Result.Destination(res)
                                        }

                                if (rule.contains(">")) {
                                    Rule.ConditionalRule(result) { rating ->
                                        when (a) {
                                            "x" -> rating.x > right
                                            "m" -> rating.m > right
                                            "a" -> rating.a > right
                                            "s" -> rating.s > right
                                            else -> throw Exception("Unknown field $a")
                                        }
                                    }
                                } else {
                                    Rule.ConditionalRule(result) { rating ->
                                        when (a) {
                                            "x" -> rating.x < right
                                            "m" -> rating.m < right
                                            "a" -> rating.a < right
                                            "s" -> rating.s < right
                                            else -> throw Exception("Unknown field $a")
                                        }
                                    }
                                }
                            } else {
                                val result =
                                        when (rule) {
                                            "A" -> Rule.Result.Accept
                                            "R" -> Rule.Result.Reject
                                            else -> Rule.Result.Destination(rule)
                                        }

                                Rule(result)
                            }
                        }

                id to Workflow(id, rules)
            }
    val parts =
            b.split("\n").map {
                // {x=2127,m=1623,a=2188,s=1013}
                val (x, m, a, s) =
                        it.substringAfter("{").substringBefore("}").split(",").map {
                            it.substringAfter("=").toInt()
                        }

                Part(x, m, a, s)
            }

    val totalParts =
            parts.filter { rating ->
                println(rating)
                val workflowQueue: Queue<Workflow> = LinkedList()

                workflowQueue.add(workflows["in"]!!)

                while (workflowQueue.isNotEmpty()) {
                    val workflow = workflowQueue.remove()

                    print("${workflow.id}->")

                    workflow.rules.forEach { rule ->
                        if (rule is Rule.ConditionalRule) {
                            if (rule.condition(rating)) {
                                when (rule.result) {
                                    is Rule.Result.Destination -> {
                                        println("D + ${workflows[rule.result.dest]!!}")
                                        workflowQueue.add(workflows[rule.result.dest]!!)
                                    }
                                    is Rule.Result.Accept -> {
                                        println("A")
                                        return@filter true
                                    }
                                    is Rule.Result.Reject -> {
                                        println("R")
                                        return@filter false
                                    }
                                }
                            }
                        } else {
                            when (rule.result) {
                                is Rule.Result.Destination -> {
                                    val dest = workflows[rule.result.dest]!!
                                    workflowQueue.add(dest)
                                }
                                is Rule.Result.Accept -> {
                                    return@filter true
                                }
                                is Rule.Result.Reject -> {
                                    return@filter false
                                }
                            }
                        }
                    }
                }

                false
            }

    val totalRatings = totalParts.sumOf { it.x + it.m + it.a + it.s }

    println("Part 1: $totalRatings")
}

data class Workflow(val id: String, val rules: List<Rule>)

data class Part(val x: Int, val m: Int, val a: Int, val s: Int)

open class Rule(val result: Result) {
    class ConditionalRule(result: Result, val condition: (Part) -> Boolean) : Rule(result)

    sealed interface Result {
        data class Destination(val dest: String) : Result
        object Accept : Result
        object Reject : Result
    }
}
