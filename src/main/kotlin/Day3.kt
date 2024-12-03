package org.example

import java.io.File

private val lines = File("input/day3/input.txt").readLines()
fun puzzle3(): Int {
    return lines.sumOf {
        "mul\\((\\d{1,3}),(\\d{1,3})\\)".toRegex().findAll(it).sumOf {
            it.groups[1]!!.value.toInt() * it.groups[2]!!.value.toInt()
        }
    }
}

fun puzzle3dot1(): Int {
    return lines.sumOf {
        val dos = "do\\(\\)".toRegex().findAll(it)
        val donts = "don't\\(\\)".toRegex().findAll(it)

        "mul\\((\\d{1,3}),(\\d{1,3})\\)".toRegex().findAll(it).sumOf { numberMatch ->
            val previousDo = dos.lastOrNull { numberMatch.range.first > it.range.first }
            val previousDont = donts.lastOrNull { numberMatch.range.first > it.range.first }

            if((previousDo?.range?.first ?: 0) >= (previousDont?.range?.first ?: 0))
                numberMatch.groups[1]!!.value.toInt() * numberMatch.groups[2]!!.value.toInt()
            else
                0
        }
    }
}
