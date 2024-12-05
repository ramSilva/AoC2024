package org.example

import java.io.File

private val lines = File("input/day5/input.txt").readLines()
fun puzzle5(): Int {
    val rules = mutableListOf<Pair<Int, Int>>()

    var parsingRules = true
    var sum = 0
    lines.forEach {
        if (it.isEmpty()) {
            parsingRules = false
            return@forEach
        }

        if (parsingRules) {
            val (rule1, rule2) = "(\\d+)\\|(\\d+)".toRegex().findAll(it).first().destructured
            rules.add(Pair(rule1.toInt(), rule2.toInt()))
        } else {
            var isValid = true
            val numbers = it.split(",").map { it.toInt() }
            numbers.forEachIndexed { index, n1 ->
                numbers.subList(index, numbers.size).forEach { n2 ->
                    if (rules.contains(Pair(n2, n1))) isValid = false
                }
            }
            if (isValid) {
                sum += numbers[numbers.size / 2]
            }
        }
    }
    return sum
}

fun puzzle5dot1(): Int {
    val rules = mutableListOf<Pair<Int, Int>>()

    var parsingRules = true
    var sum = 0
    lines.forEach {
        if (it.isEmpty()) {
            parsingRules = false
            return@forEach
        }

        if (parsingRules) {
            val (rule1, rule2) = "(\\d+)\\|(\\d+)".toRegex().findAll(it).first().destructured
            rules.add(Pair(rule1.toInt(), rule2.toInt()))
        } else {
            var isValid = true
            val numbers = it.split(",").map { it.toInt() }.toMutableList()

            for (i1 in 0..<numbers.size) {
                for (i2 in i1 + 1..<numbers.size) {
                    val n1 = numbers[i1]
                    val n2 = numbers[i2]

                    if (rules.contains(Pair(n2, n1))) {
                        isValid = false

                        numbers[i1] = n2
                        numbers[i2] = n1
                    }
                }
            }

            if (!isValid) {
                sum += numbers[numbers.size / 2]
            }
        }
    }
    return sum
}
