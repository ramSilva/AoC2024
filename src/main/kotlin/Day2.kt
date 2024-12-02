package org.example

import java.io.File

private val lines = File("input/day2/input.txt").readLines()
fun puzzle2(): Int {
    return lines.count {
        isSafe(it.split(' ').map { it.toInt() })
    }
}

fun puzzle2dot1(): Int {
    return lines.count {
        val numbers = it.split(' ').map { it.toInt() }

        val dampened = mutableListOf(numbers)

        for (i in numbers.indices) {
            val dup = numbers.toMutableList()
            dup.removeAt(i)
            dampened.add(dup)
        }

        dampened.any { isSafe(it) }
    }
}

private fun isSafe(list: List<Int>): Boolean {
    val windows = list.windowed(2, 1)
    return windows.all {
        it[0] - it[1] in 1..3
    } || windows.all {
        it[0] - it[1] in -3..-1
    }
}