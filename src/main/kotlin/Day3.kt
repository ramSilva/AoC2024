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

fun puzzle3dot2(): Int {
    var enabled = true
    return lines.sumOf {
        "mul\\((\\d{1,3}),(\\d{1,3})\\)|do\\(\\)|don't\\(\\)".toRegex().findAll(it).sumOf inner@ {
            if(it.value == "do()") {
                enabled = true
                return@inner 0
            }
            if(it.value == "don't()"){
                enabled = false
                return@inner 0
            }

            if(enabled){
                return@inner it.groups[1]!!.value.toInt() * it.groups[2]!!.value.toInt()
            }

            0
        }
    }
}
