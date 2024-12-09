package org.example

import java.io.File

private val lines = File("input/day9/input.txt").readLines()

fun puzzle9(): Long {
    val disk = mutableListOf<Int>()
    var parsingFile = true
    var id = 0

    lines.first().forEach {
        for (i in 0..<it.digitToInt()) {
            if (parsingFile)
                disk.add(id)
            else
                disk.add(-1)
        }

        if (parsingFile) id++
        parsingFile = !parsingFile
    }

    while (true) {
        val lastValueIndex = disk.indexOfLast { it != -1 }
        val firstEmptyIndex = disk.indexOfFirst { it == -1 }

        if (firstEmptyIndex > lastValueIndex) break

        disk[firstEmptyIndex] = disk[lastValueIndex]
        disk[lastValueIndex] = -1
    }

    var sum = 0L

    disk.filter { it != -1 }.forEachIndexed { index, value ->
        sum += value * index
    }
    return sum
}

fun puzzle9dot1(): Long {
    val disk = mutableListOf<Int>()
    var parsingFile = true
    var id = 0

    lines.first().forEach {
        for (i in 0..<it.digitToInt()) {
            if (parsingFile)
                disk.add(id)
            else
                disk.add(-1)
        }

        if (parsingFile) id++
        parsingFile = !parsingFile
    }

    for (fileId in id - 1 downTo 0) {
        var isEmpty = false
        var chunkIndex = -1
        var chunkSize = -1

        val fileStart = disk.indexOfFirst { it == fileId }
        val fileEnd = disk.indexOfLast { it == fileId }
        val fileSize = fileEnd - fileStart

        for (i in 0..<disk.size) {
            val value = disk[i]
            if (value != -1) {
                chunkIndex = -1
                chunkSize = -1
                isEmpty = false
            }

            if (value == -1) {
                if (!isEmpty) chunkIndex = i
                chunkSize++
                isEmpty = true
            }

            if (chunkSize >= fileSize && chunkIndex < fileStart) {
                for (delta in 0..fileSize) {
                    disk[chunkIndex + delta] = fileId
                    disk[fileStart + delta] = -1
                }
                break
            }
        }
    }

    var sum = 0L
    disk.forEachIndexed { index, value ->
        if (value != -1)
            sum += value * index
    }
    return sum
}
