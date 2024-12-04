package org.example

import java.io.File

private val lines = File("input/day4/input.txt").readLines()
fun puzzle4(): Int {
    var count = 0
    lines.forEachIndexed { y, s ->
        s.forEachIndexed { x, c ->
            // horizontal left right
            if (c == 'X' && x <= s.length - 4 && s[x + 1] == 'M' && s[x + 2] == 'A' && s[x + 3] == 'S')
                count++

            // horizontal right left
            if ((c == 'X' && x >= 3 && s[x - 1] == 'M' && s[x - 2] == 'A' && s[x - 3] == 'S'))
                count++

            // vertical up down
            if ((c == 'X' && y <= lines.size - 4 && lines[y + 1][x] == 'M' && lines[y + 2][x] == 'A' && lines[y + 3][x] == 'S'))
                count++

            // vertical down up
            if ((c == 'X' && y >= 3 && lines[y - 1][x] == 'M' && lines[y - 2][x] == 'A' && lines[y - 3][x] == 'S'))
                count++

            // diagonal topleft bottomright
            if ((c == 'X' && x <= s.length - 4 && y <= lines.size - 4 && lines[y + 1][x + 1] == 'M' && lines[y + 2][x + 2] == 'A' && lines[y + 3][x + 3] == 'S'))
                count++

            // diagonal bottomright topleft
            if ((c == 'X' && x >= 3 && y >= 3 && lines[y - 1][x - 1] == 'M' && lines[y - 2][x - 2] == 'A' && lines[y - 3][x - 3] == 'S'))
                count++

            // diagonal bottomleft topright
            if ((c == 'X' && x <= s.length - 4 && y >= 3 && lines[y - 1][x + 1] == 'M' && lines[y - 2][x + 2] == 'A' && lines[y - 3][x + 3] == 'S'))
                count++

            // diagonal topright bottomleft
            if ((c == 'X' && x >= 3 && y <= lines.size - 4 && lines[y + 1][x - 1] == 'M' && lines[y + 2][x - 2] == 'A' && lines[y + 3][x - 3] == 'S'))
                count++
        }
    }

    return count
}

fun puzzle4dot1(): Int {
    var count = 0
    lines.forEachIndexed { y, s ->
        s.forEachIndexed { x, c ->
            //topleft bottomright + bottomleft topright
            if (c == 'A' && x in 1..s.length - 2 && y in 1..lines.size - 2 &&
                lines[y - 1][x - 1] == 'M' && lines[y + 1][x + 1] == 'S' && lines[y + 1][x - 1] == 'M' && lines[y - 1][x + 1] == 'S'
            )
                count++

            //topleft bottomright + topright bottomleft
            if (c == 'A' && x in 1..s.length - 2 && y in 1..lines.size - 2 &&
                lines[y - 1][x - 1] == 'M' && lines[y + 1][x + 1] == 'S' && lines[y - 1][x + 1] == 'M' && lines[y + 1][x - 1] == 'S'
            )
                count++

            //bottomright topleft + bottomleft topright
            if (c == 'A' && x in 1..s.length - 2 && y in 1..lines.size - 2 &&
                lines[y + 1][x + 1] == 'M' && lines[y - 1][x - 1] == 'S' && lines[y + 1][x - 1] == 'M' && lines[y - 1][x + 1] == 'S'
            )
                count++

            //bottomright topleft + topright bottomleft
            if (c == 'A' && x in 1..s.length - 2 && y in 1..lines.size - 2 &&
                lines[y + 1][x + 1] == 'M' && lines[y - 1][x - 1] == 'S' && lines[y - 1][x + 1] == 'M' && lines[y + 1][x - 1] == 'S'
            )
                count++
        }
    }

    return count
}
