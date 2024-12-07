package org.example

import java.io.File

private val lines = File("input/day7/input.txt").readLines()

fun puzzle7(): Long {
    var sum = 0L

    lines.forEach {
        val (expectedResult, rest) = "(\\d+): (.*)".toRegex().find(it)?.destructured!!

        val expectedResultAsLong = expectedResult.toLong()
        val restAsLong = rest.split(" ").map { it.toLong() }
        val results = restAsLong.fold(listOf(0L)){ runningTotals, i ->
            runningTotals.flatMap {
                listOf(
                    it + i,
                    it * i
                )
            }.filter { it != 0L && it <= expectedResultAsLong}
        }
        if(results.contains(expectedResultAsLong)) sum += expectedResultAsLong
    }

    return sum
}

fun puzzle7dot1(): Long {
    var sum = 0L

    lines.forEach {
        val (expectedResult, rest) = "(\\d+): (.*)".toRegex().find(it)?.destructured!!

        val expectedResultAsLong = expectedResult.toLong()
        val restAsLong = rest.split(" ").map { it.toLong() }
        val results = restAsLong.fold(listOf(0L)){ runningTotals, i ->
            runningTotals.flatMap {
                listOf(
                    it + i,
                    it * i,
                    "$it$i".toLong()
                )
            }.filter { it != 0L && it <= expectedResultAsLong}
        }
        if(results.contains(expectedResultAsLong)) sum += expectedResultAsLong
    }

    return sum
}
