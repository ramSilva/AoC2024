package org.example

import java.io.File

private val lines = File("input/day6/input.txt").readLines()

private val directions = listOf(
    Pair(0, -1),
    Pair(1, 0),
    Pair(0, 1),
    Pair(-1, 0)
)

fun puzzle6(): Int {
    val obstacles = mutableListOf<Pair<Int, Int>>()
    var visited = mutableSetOf<Pair<Int, Int>>()
    var mapDimensionX = -1
    var mapDimensionY = -1

    var currentDirectionIndex = 0
    var currentDirection = directions[currentDirectionIndex]

    var currentPosition = Pair(-1, -1)

    lines.forEachIndexed { y, s ->
        if (y > mapDimensionY) mapDimensionY = y

        s.forEachIndexed { x, c ->
            if (x > mapDimensionX) mapDimensionX = x
            val checkingPosition = Pair(x, y)
            when (c) {
                '#' -> obstacles.add(checkingPosition)
                '^' -> currentPosition = checkingPosition
            }
        }
    }

    while (true) {
        visited.add(currentPosition)
        var nextPosition = Pair(
            currentPosition.first + currentDirection.first,
            currentPosition.second + currentDirection.second
        )

        if (obstacles.contains(nextPosition)) {
            currentDirectionIndex = (currentDirectionIndex + 1) % 4
            currentDirection = directions[currentDirectionIndex]

            nextPosition = Pair(
                currentPosition.first + currentDirection.first,
                currentPosition.second + currentDirection.second
            )
        }

        currentPosition = nextPosition

        if (currentPosition.first > mapDimensionX || currentPosition.second > mapDimensionY) break
    }

    return visited.size
}

fun puzzle6dot1(): Int {
    val obstacles = mutableListOf<Pair<Int, Int>>()
    var ogvisited = mutableSetOf<Pair<Int, Int>>()
    var mapDimensionX = -1
    var mapDimensionY = -1

    var currentDirectionIndex = 0
    var currentDirection = directions[currentDirectionIndex]

    var originalPosition = Pair(-1, -1)
    var currentPosition = Pair(-1, -1)

    lines.forEachIndexed { y, s ->
        if (y > mapDimensionY) mapDimensionY = y

        s.forEachIndexed { x, c ->
            if (x > mapDimensionX) mapDimensionX = x
            val checkingPosition = Pair(x, y)
            when (c) {
                '#' -> obstacles.add(checkingPosition)
                '^' -> {
                    originalPosition = checkingPosition
                    currentPosition = originalPosition
                }
            }
        }
    }

    // pt1 to know where the guard goes without added obstacles
    while (true) {
        ogvisited.add(currentPosition)
        var nextPosition = Pair(
            currentPosition.first + currentDirection.first,
            currentPosition.second + currentDirection.second
        )

        if (obstacles.contains(nextPosition)) {
            currentDirectionIndex = (currentDirectionIndex + 1) % 4
            currentDirection = directions[currentDirectionIndex]

            nextPosition = Pair(
                currentPosition.first + currentDirection.first,
                currentPosition.second + currentDirection.second
            )
        }

        currentPosition = nextPosition

        if (
            currentPosition.first > mapDimensionX ||
            currentPosition.first < 0 ||
            currentPosition.second > mapDimensionY ||
            currentPosition.second < 0
        )
            break
    }

    //pt2
    val loopObstacles = mutableListOf<Pair<Int, Int>>()
    ogvisited.forEach { obstaclePos ->
        if (originalPosition == obstaclePos) return@forEach

        currentPosition = originalPosition
        currentDirectionIndex = 0
        currentDirection = directions[currentDirectionIndex]

        obstacles.add(obstaclePos)
        val visited = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>() // position/direction

        while (true) {
            if (visited.contains(Pair(currentPosition, currentDirection))) {
                loopObstacles.add(obstaclePos)
                break
            }

            visited.add(Pair(currentPosition, currentDirection))

            var nextPosition = Pair(
                currentPosition.first + currentDirection.first,
                currentPosition.second + currentDirection.second
            )

            if (obstacles.contains(nextPosition)) {
                currentDirectionIndex = (currentDirectionIndex + 1) % 4
                currentDirection = directions[currentDirectionIndex]

                nextPosition = currentPosition
            }

            currentPosition = nextPosition

            if (
                currentPosition.first > mapDimensionX ||
                currentPosition.first < 0 ||
                currentPosition.second > mapDimensionY ||
                currentPosition.second < 0
            )
                break
        }

        obstacles.remove(obstaclePos)
    }


    return loopObstacles.size
}
