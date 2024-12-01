package org.example
import java.io.File
import kotlin.math.abs

private val lines = File("input/day1/input.txt").readLines()
fun puzzle1(): Int {
    val l1 = mutableListOf<Int>()
    val l2 = mutableListOf<Int>()
    lines.forEach {
        val split = it.split("\\s+".toRegex())
        l1.add(split[0].toInt())
        l2.add(split[1].toInt())
    }

    l1.sort()
    l2.sort()

    var sum = 0
    l1.forEachIndexed { i, n ->
        sum += abs(n - l2[i])
    }

    return sum
}

fun puzzle1dot1(): Int {
    val l1 = mutableListOf<Int>()
    val l2 = mutableListOf<Int>()
    lines.forEach {
        val split = it.split("\\s+".toRegex())
        l1.add(split[0].toInt())
        l2.add(split[1].toInt())
    }

    var sum = 0
    l1.forEach { n1 ->
        sum += n1 * l2.count { n2 -> n2 == n1 }
    }

    return sum
}