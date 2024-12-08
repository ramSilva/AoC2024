package org.example

import java.io.File

private val lines = File("input/day8/input.txt").readLines()

private fun Pair<Int, Int>.reflect(other: Pair<Int, Int>, n: Int = 1): Pair<Int, Int> =
    Pair(this.first + (this.first - other.first) * n, this.second + (this.second - other.second) * n)

fun puzzle8(): Int {
    var mapDimensionX = -1
    var mapDimensionY = -1

    val antennas = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()

    lines.forEachIndexed { y, s ->
        if (y > mapDimensionY) mapDimensionY = y
        s.forEachIndexed { x, c ->
            if (x > mapDimensionX) mapDimensionX = x

            if (c != '.') {
                if (antennas.containsKey(c)) antennas[c]?.add(Pair(x, y))
                else antennas[c] = mutableListOf(Pair(x, y))
            }
        }
    }

    val antinodes = mutableSetOf<Pair<Int, Int>>()
    antennas.forEach { entry ->
        entry.value.forEach { currentAntenna ->
            val antennasWithoutCurrent = entry.value.toMutableList()
            antennasWithoutCurrent.remove(currentAntenna)

            antennasWithoutCurrent.forEach { otherAntenna ->
                val potentialAntinode = currentAntenna.reflect(otherAntenna)
                if(potentialAntinode.first in 0..mapDimensionX && potentialAntinode.second in 0..mapDimensionY)
                    antinodes.add(potentialAntinode)
            }
        }
    }

    return antinodes.size
}

fun puzzle8dot1(): Int {
    var mapDimensionX = -1
    var mapDimensionY = -1

    val antennas = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()

    lines.forEachIndexed { y, s ->
        if (y > mapDimensionY) mapDimensionY = y
        s.forEachIndexed { x, c ->
            if (x > mapDimensionX) mapDimensionX = x

            if (c != '.') {
                if (antennas.containsKey(c)) antennas[c]?.add(Pair(x, y))
                else antennas[c] = mutableListOf(Pair(x, y))
            }
        }
    }

    val antinodes = mutableSetOf<Pair<Int, Int>>()
    antennas.forEach { entry ->
        entry.value.forEach { currentAntenna ->
            val antennasWithoutCurrent = entry.value.toMutableList()
            antennasWithoutCurrent.remove(currentAntenna)

            antennasWithoutCurrent.forEach { otherAntenna ->
                var n = 0
                while(true){
                    val potentialAntinode = currentAntenna.reflect(otherAntenna, n)
                    if (potentialAntinode.first in 0..mapDimensionX && potentialAntinode.second in 0..mapDimensionY)
                        antinodes.add(potentialAntinode)
                    else break
                    n++
                }
            }
        }
    }

    return antinodes.size
}
