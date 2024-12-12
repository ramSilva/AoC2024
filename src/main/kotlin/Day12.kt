package org.example

import java.io.File

private val lines = File("input/day12/input.txt").readLines()

private fun Pair<Int, Int>.adjacent() = listOf(
    Pair(this.first - 1, this.second),
    Pair(this.first + 1, this.second),
    Pair(this.first, this.second - 1),
    Pair(this.first, this.second + 1),
)

fun puzzle12(): Long {
    val checkedPlots = mutableListOf<Pair<Int, Int>>()

    val mapLength = lines[0].length
    val mapHeight = lines.size

    var sum = 0L

    lines.forEachIndexed { y, s ->
        s.forEachIndexed { x, c ->
            val currentPlot = Pair(x, y)

            if (!checkedPlots.contains(currentPlot)) {
                val currentArea = mutableListOf(currentPlot)
                var adjacent = currentPlot.adjacent()

                var perimeter = 0

                while (adjacent.isNotEmpty()) {
                    val adjacentCopy = adjacent.toMutableList()
                    adjacent.forEach {
                        adjacentCopy.remove(it)
                        if (it in currentArea) return@forEach
                        if (it.first < 0 || it.second < 0 || it.first >= mapLength || it.second >= mapHeight) {
                            perimeter++
                            return@forEach
                        }
                        if (lines[it.second][it.first] != c) {
                            perimeter++
                            return@forEach
                        }
                        if (lines[it.second][it.first] == c) {
                            currentArea.add(it)
                            adjacentCopy.addAll(it.adjacent())
                        }
                    }
                    adjacent = adjacentCopy
                }

                sum += currentArea.size * perimeter
                checkedPlots.addAll(currentArea)
            }
        }
    }
    return sum
}

fun puzzle12dot1(): Long {
    val checkedPlots = mutableListOf<Pair<Int, Int>>()
    val mapLength = lines[0].length
    val mapHeight = lines.size

    var sum = 0L

    lines.forEachIndexed { y, s ->
        s.forEachIndexed { x, c ->
            val currentPlot = Pair(x, y)

            if (!checkedPlots.contains(currentPlot)) {
                val currentArea = mutableListOf(currentPlot)

                var areaTop = currentPlot.second
                var areaBottom = currentPlot.second
                var areaLeft = currentPlot.first
                var areaRight = currentPlot.first

                var adjacent = currentPlot.adjacent()

                var sides = 0

                while (adjacent.isNotEmpty()) {
                    val adjacentCopy = adjacent.toMutableList()
                    adjacent.forEach {
                        adjacentCopy.remove(it)
                        if (it in currentArea) return@forEach
                        if (it.first < 0 || it.second < 0 || it.first >= mapLength || it.second >= mapHeight) {
                            return@forEach
                        }
                        if (lines[it.second][it.first] != c) {
                            return@forEach
                        }
                        if (lines[it.second][it.first] == c) {
                            if (it.first < areaLeft) areaLeft = it.first
                            if (it.first > areaRight) areaRight = it.first
                            if (it.second < areaTop) areaTop = it.second
                            if (it.second > areaBottom) areaBottom = it.second

                            currentArea.add(it)
                            adjacentCopy.addAll(it.adjacent())
                        }
                    }
                    adjacent = adjacentCopy
                }

                for (i in areaTop..areaBottom) {
                    var isSideAbove = false
                    var isSideBelow = false
                    for (j in areaLeft..areaRight) {
                        val sideCheckingPosition = Pair(j, i)

                        if (sideCheckingPosition !in currentArea) {
                            isSideAbove = false
                            isSideBelow = false
                            continue
                        }

                        val abovePosition = Pair(sideCheckingPosition.first, sideCheckingPosition.second - 1)
                        val belowPosition = Pair(sideCheckingPosition.first, sideCheckingPosition.second + 1)

                        if (!isSideAbove && abovePosition !in currentArea) {
                            sides++
                            isSideAbove = true
                        }
                        if (!isSideBelow && belowPosition !in currentArea) {
                            sides++
                            isSideBelow = true
                        }

                        if (isSideAbove && abovePosition in currentArea) {
                            isSideAbove = false
                        }
                        if (isSideBelow && belowPosition in currentArea) {
                            isSideBelow = false
                        }
                    }
                }

                for (i in areaLeft..areaRight) {
                    var isSideLeft = false
                    var isSideRight = false
                    for (j in areaTop..areaBottom) {
                        val sideCheckingPosition = Pair(i, j)

                        if (sideCheckingPosition !in currentArea) {
                            isSideLeft = false
                            isSideRight = false
                            continue
                        }

                        val leftPosition = Pair(sideCheckingPosition.first - 1, sideCheckingPosition.second)
                        val rightPosition = Pair(sideCheckingPosition.first + 1, sideCheckingPosition.second)

                        if (!isSideLeft && leftPosition !in currentArea) {
                            sides++
                            isSideLeft = true
                        }
                        if (!isSideRight && rightPosition !in currentArea) {
                            sides++
                            isSideRight = true
                        }

                        if (isSideLeft && leftPosition in currentArea) {
                            isSideLeft = false
                        }
                        if (isSideRight && rightPosition in currentArea) {
                            isSideRight = false
                        }
                    }
                }

                sum += currentArea.size * sides
                checkedPlots.addAll(currentArea)
            }
        }
    }
    return sum
}
