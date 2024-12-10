package org.example

import java.io.File

private val lines = File("input/day10/input.txt").readLines()

fun puzzle10(): Int {
    val map = mutableMapOf<Pair<Int, Int>, Int>()

    val mapLength = lines[0].length
    val mapHeight = lines.size

    lines.forEachIndexed { y, s ->
        s.forEachIndexed { x, c ->
            map[Pair(x, y)] = c.digitToInt()
        }
    }

    var totalScore = 0
    for (x in 0..mapLength) {
        for (y in 0..mapHeight) {
            val currentHeight = map[Pair(x, y)]

            if (currentHeight == 0) {
                val trail = mutableListOf(Pair(x, y))
                val peaks = mutableSetOf<Pair<Int, Int>>()

                while (trail.isNotEmpty()) {
                    val currentTrailPos = trail.removeFirst()
                    val currentTrailHeight = map[currentTrailPos]!!

                    if (currentTrailHeight == 9) {
                        if (!peaks.contains(currentTrailPos)) totalScore++
                        peaks.add(currentTrailPos)
                        continue
                    }

                    for (adjacentX in currentTrailPos.first - 1..currentTrailPos.first + 1) {
                        val adjacentHeight = map[Pair(adjacentX, currentTrailPos.second)]

                        if (adjacentHeight == (currentTrailHeight + 1)) {
                            trail.add(Pair(adjacentX, currentTrailPos.second))
                        }
                    }

                    for (adjacentY in currentTrailPos.second - 1..currentTrailPos.second + 1) {
                        val adjacentHeight = map[Pair(currentTrailPos.first, adjacentY)]

                        if (adjacentHeight == (currentTrailHeight + 1)) {
                            trail.add(Pair(currentTrailPos.first, adjacentY))
                        }
                    }
                }
            }
        }
    }

    return totalScore
}

fun puzzle10dot1(): Int {
    val map = mutableMapOf<Pair<Int, Int>, Int>()

    val mapLength = lines[0].length
    val mapHeight = lines.size

    lines.forEachIndexed { y, s ->
        s.forEachIndexed { x, c ->
            map[Pair(x, y)] = c.digitToInt()
        }
    }

    var totalScore = 0
    for (x in 0..mapLength) {
        for (y in 0..mapHeight) {
            val currentHeight = map[Pair(x, y)]

            if (currentHeight == 0) {
                val trail = mutableListOf(Pair(x, y))

                while (trail.isNotEmpty()) {
                    val currentTrailPos = trail.removeFirst()
                    val currentTrailHeight = map[currentTrailPos]!!

                    if (currentTrailHeight == 9) {
                        totalScore++
                        continue
                    }

                    for (adjacentX in currentTrailPos.first - 1..currentTrailPos.first + 1) {
                        val adjacentHeight = map[Pair(adjacentX, currentTrailPos.second)]

                        if (adjacentHeight == (currentTrailHeight + 1)) {
                            trail.add(Pair(adjacentX, currentTrailPos.second))
                        }
                    }

                    for (adjacentY in currentTrailPos.second - 1..currentTrailPos.second + 1) {
                        val adjacentHeight = map[Pair(currentTrailPos.first, adjacentY)]

                        if (adjacentHeight == (currentTrailHeight + 1)) {
                            trail.add(Pair(currentTrailPos.first, adjacentY))
                        }
                    }
                }
            }
        }
    }

    return totalScore
}
