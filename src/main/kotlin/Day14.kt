package org.example

import java.awt.Dimension
import java.awt.image.BufferedImage
import java.io.File
import java.io.PrintWriter
import javax.imageio.ImageIO

private val lines = File("input/day14/input.txt").readLines()

fun puzzle14(): Int {
    val gridDimension = Pair(101, 103)
    var robots = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()

    val regex = "p=(\\d+,\\d+) v=(-?\\d+,-?\\d+)".toRegex()

    lines.forEach {
        val (positionMatch, velocityMatch) = regex.find(it)!!.destructured
        val position = positionMatch.split(',').map { it.toInt() }
        val velocity = velocityMatch.split(',').map { it.toInt() }

        robots.add(Pair(Pair(position[0], position[1]), Pair(velocity[0], velocity[1])))
    }

    for (i in 0..<100) {
        val robotsCopy = robots.toMutableList()
        robots.forEach {
            val currentPosition = it.first
            val velocity = it.second
            var nextX = (currentPosition.first + velocity.first) % gridDimension.first
            var nextY = (currentPosition.second + velocity.second) % gridDimension.second
            if (nextX < 0) nextX += gridDimension.first
            if (nextY < 0) nextY += gridDimension.second

            robotsCopy.remove(it)
            robotsCopy.add(Pair(Pair(nextX, nextY), velocity))
        }
        robots = robotsCopy
    }

    val quad1 = Pair(
        0..((gridDimension.first / 2) - 1), 0..((gridDimension.second / 2) - 1)
    )
    val quad2 = Pair(
        ((gridDimension.first / 2) + 1)..gridDimension.first, 0..((gridDimension.second / 2) - 1)
    )
    val quad3 = Pair(
        0..((gridDimension.first / 2) - 1), ((gridDimension.second / 2) + 1)..gridDimension.second
    )
    val quad4 = Pair(
        ((gridDimension.first / 2) + 1)..gridDimension.first, ((gridDimension.second / 2) + 1)..gridDimension.second
    )

    return robots.count {
        val position = it.first

        position.first in quad1.first && position.second in quad1.second
    } * robots.count {
        val position = it.first

        position.first in quad2.first && position.second in quad2.second
    } * robots.count {
        val position = it.first

        position.first in quad3.first && position.second in quad3.second
    } * robots.count {
        val position = it.first

        position.first in quad4.first && position.second in quad4.second
    }
}

fun puzzle14dot1() {
    val gridDimension = Pair(101, 103)
    var robots = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()

    val regex = "p=(\\d+,\\d+) v=(-?\\d+,-?\\d+)".toRegex()

    lines.forEach {
        val (positionMatch, velocityMatch) = regex.find(it)!!.destructured
        val position = positionMatch.split(',').map { it.toInt() }
        val velocity = velocityMatch.split(',').map { it.toInt() }

        robots.add(Pair(Pair(position[0], position[1]), Pair(velocity[0], velocity[1])))
    }

    for (i in 0..<10000) {
        val robotsCopy = robots.toMutableList()
        robots.forEach {
            val currentPosition = it.first
            val velocity = it.second
            var nextX = (currentPosition.first + velocity.first) % gridDimension.first
            var nextY = (currentPosition.second + velocity.second) % gridDimension.second
            if (nextX < 0) nextX += gridDimension.first
            if (nextY < 0) nextY += gridDimension.second

            robotsCopy.remove(it)
            robotsCopy.add(Pair(Pair(nextX, nextY), velocity))
        }
        robots = robotsCopy

        val size = Dimension(gridDimension.first, gridDimension.second)
        val img = BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB)

        for (x in 0..<gridDimension.first) {
            for (y in 0..<gridDimension.second) {
                if (robots.any { it.first == Pair(x, y) }) img.setRGB(x, y, 0x000000) else img.setRGB(x, y, 0xFFFFFF)
            }
        }
        ImageIO.write(img, "BMP", File("day14/$i.bmp"))
    }
}
