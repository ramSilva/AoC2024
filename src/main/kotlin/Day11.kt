package org.example

import java.io.File

private val lines = File("input/day11/input.txt").readLines()

fun puzzle11(): Long {
    val stonesList = lines[0].split(' ').map { it.toLong() }.toMutableList()
    var stones = mutableMapOf<Long, Long>()
    stonesList.forEach {
        if (stones.containsKey(it)) stones[it] = stones[it]!! + 1
        else stones[it] = 1
    }

    for (i in 0..<75) {
        val stonesCopy = stones.toMutableMap()
        stones.forEach {
            stonesCopy[it.key] = stonesCopy[it.key]!! - it.value
            if(stonesCopy[it.key] == 0L) stonesCopy.remove(it.key)

            if (it.key == 0L) {
                stonesCopy[1] = (stonesCopy[1] ?: 0) + it.value
            } else if (it.key.toString().length % 2 == 0) {
                val leftHalf = it.key.toString().take(it.key.toString().length / 2).toLong()
                val rightHalf = it.key.toString().takeLast(it.key.toString().length / 2).toLong()

                stonesCopy[leftHalf] = (stonesCopy[leftHalf] ?: 0) + it.value
                stonesCopy[rightHalf] = (stonesCopy[rightHalf] ?: 0) + it.value
            } else {
                stonesCopy[it.key * 2024] = (stonesCopy[it.key * 2024] ?: 0) + it.value
            }
        }
        stones = stonesCopy
    }

    var sum = 0L
    stones.forEach {
        sum += it.value
    }
    return sum
}

fun puzzle11dot1(): Int {
    return 0
}
