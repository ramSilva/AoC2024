package org.example

import java.io.File
import javax.imageio.ImageIO

private val lines = File("input/day15/input.txt").readLines()

private val directions = mapOf(
    '<' to Pair(-1, 0),
    '^' to Pair(0, -1),
    'v' to Pair(0, 1),
    '>' to Pair(1, 0)
)

private operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) =
    Pair(this.first + other.first, this.second + other.second)

private operator fun Pair<Int, Int>.minus(other: Pair<Int, Int>) =
    Pair(this.first - other.first, this.second - other.second)

fun puzzle15(): Long {
    var parsingMap = true
    val map = mutableMapOf<Pair<Int, Int>, Char>()
    var robotPosition = Pair(0, 0)

    lines.forEachIndexed { y, s ->
        if (s.isEmpty()) parsingMap = false

        if (parsingMap) {
            s.forEachIndexed { x, c ->
                if (c == '@') {
                    robotPosition = Pair(x, y)
                    map[Pair(x, y)] = '.'
                } else map[Pair(x, y)] = c
            }
        } else {
            s.forEach { instruction ->
                val direction = directions[instruction]!!

                var checkingPosition = robotPosition + direction
                var firstBlockToPush: Pair<Int, Int>? = null

                while (true) {
                    if (map[checkingPosition] == '#') break
                    if (map[checkingPosition] == '.') {
                        if (firstBlockToPush != null) {
                            map[firstBlockToPush] = '.'
                            map[checkingPosition] = 'O'

                            robotPosition = firstBlockToPush
                        } else {
                            robotPosition = checkingPosition
                        }
                        break
                    }
                    if (map[checkingPosition] == 'O') {
                        if (firstBlockToPush == null) firstBlockToPush = checkingPosition
                    }

                    checkingPosition += direction
                }
            }
        }
    }

    var sum = 0L

    map.forEach {
        if (it.value == 'O') {
            sum += it.key.first + it.key.second * 100
        }
    }

    return sum
}

fun puzzle15dot1(): Long {
    var parsingMap = true
    var map = mutableMapOf<Pair<Int, Int>, Char>()
    var robotPosition = Pair(0, 0)

    lines.forEachIndexed { y, s ->

        if (s.isEmpty()) {
            parsingMap = false
            return@forEachIndexed
        }

        if (parsingMap) {
            var wideString = s.replace("#", "##")
            wideString = wideString.replace(".", "..")
            wideString = wideString.replace("O", "[]")
            wideString = wideString.replace("@", "@.")

            wideString.forEachIndexed { x, c ->
                if (c == '@') {
                    robotPosition = Pair(x, y)
                    map[Pair(x, y)] = '.'
                } else map[Pair(x, y)] = c
            }
        } else {
            s.forEach { instruction ->
                val direction = directions[instruction]!!

                val positionsToCheck = mutableListOf(robotPosition + direction)
                val positionsChecked = positionsToCheck.toMutableList()

                val boxesToPush = mutableListOf<Pair<Int, Int>>()
                while (positionsToCheck.isNotEmpty()) {
                    val checkingPosition = positionsToCheck.removeFirst()

                    if (map[checkingPosition] == '.' && positionsToCheck.isEmpty()) {
                        val canPush = boxesToPush.all { map[it+direction] != '#' }
                        if(canPush) {
                            robotPosition = if (boxesToPush.isNotEmpty()) boxesToPush.last()
                            else checkingPosition

                            val mapCopy = map.toMutableMap()
                            if (instruction == 'v' || instruction == '^') {
                                while (boxesToPush.isNotEmpty()) {
                                    val box = boxesToPush.removeFirst()
                                    if (map[box] == '[' && mapCopy[box + direction + Pair(1, 0)] != '#') {
                                        mapCopy[box + direction] = '['
                                        mapCopy[box] = '.'
                                    } else if (map[box] == ']' && mapCopy[box + direction + Pair(-1, 0)] != '#') {
                                        mapCopy[box + direction] = ']'
                                        mapCopy[box] = '.'
                                    }
                                }
                            } else {
                                boxesToPush.forEach { box ->
                                    if (box == boxesToPush.last()) {
                                        mapCopy[boxesToPush.last()] = '.'
                                    }
                                    if (map[box] == '[') {
                                        mapCopy[box + direction] = '['
                                    } else if (map[box] == ']') {
                                        mapCopy[box + direction] = ']'
                                    }
                                }
                            }

                            map = mapCopy
                        }
                        break
                    }
                    if (map[checkingPosition] == '[' || map[checkingPosition] == ']') {
                        if (!boxesToPush.contains(checkingPosition)) boxesToPush.add(0, checkingPosition)

                        if (map[checkingPosition] == '[') {
                            if (!positionsChecked.contains(checkingPosition + Pair(1, 0))) {
                                positionsToCheck.add(0, checkingPosition + Pair(1, 0))
                                positionsChecked.add(checkingPosition + Pair(1, 0))
                            }
                        } else if (map[checkingPosition] == ']') {
                            if (!positionsChecked.contains(checkingPosition + Pair(-1, 0))) {
                                positionsToCheck.add(0, checkingPosition + Pair(-1, 0))
                                positionsChecked.add(checkingPosition + Pair(-1, 0))
                            }
                        }

                        if (!positionsChecked.contains(checkingPosition + direction)) {
                            positionsToCheck.add(checkingPosition + direction)
                            positionsChecked.add(checkingPosition + direction)
                        }
                    }
                }
            }
        }
    }

    var sum = 0L

    map.forEach {
        if (it.value == '[') {
            sum += it.key.first + it.key.second * 100
        }
    }

    return sum
}
