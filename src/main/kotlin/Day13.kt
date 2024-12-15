package org.example

import java.io.File

private val lines = File("input/day13/input.txt").readLines()

data class Node(
    val aClicks: Int,
    val bClicks: Int,
    val price: Long,
    val position: Position
)
typealias Position = Pair<Long, Long>

fun puzzle13(): Long {
    val machines = lines.chunked(4)
    val buttonRegex = "X\\+(\\d+), Y\\+(\\d+)".toRegex()
    val targetRegex = "X=(\\d+), Y=(\\d+)".toRegex()

    return machines.sumOf {
        val graph = mutableMapOf<Node, Pair<Position, Position>>()

        val buttonA = Pair(
            buttonRegex.find(it[0])!!.groups[1]!!.value.toInt(),
            buttonRegex.find(it[0])!!.groups[2]!!.value.toInt()
        )
        val buttonB = Pair(
            buttonRegex.find(it[1])!!.groups[1]!!.value.toInt(),
            buttonRegex.find(it[1])!!.groups[2]!!.value.toInt()
        )
        val target = Pair(
            targetRegex.find(it[2])!!.groups[1]!!.value.toLong(),
            targetRegex.find(it[2])!!.groups[2]!!.value.toLong()
        )

        var nodesToCheck = mutableMapOf(
            Pair(0L, 0L) to
                    Node(
                        0,
                        0,
                        0,
                        Pair(0, 0)
                    )
        )

        var priceToFinish = Long.MAX_VALUE

        while (nodesToCheck.isNotEmpty()) {
            val nodesToCheckCopy = nodesToCheck.toMutableMap()
            nodesToCheck.forEach { (_, currentNode) ->
                nodesToCheckCopy.remove(currentNode.position)
                if (currentNode.position == target && currentNode.price < priceToFinish) {
                    priceToFinish = currentNode.price
                    return@forEach
                }

                if (currentNode.aClicks == 100 || currentNode.bClicks == 100 || currentNode.position.first > target.first || currentNode.position.second > target.second) return@forEach

                val positionOnClickingA =
                    Pair(currentNode.position.first + buttonA.first, currentNode.position.second + buttonA.second)
                val nodeOnClickingA =
                    Node(
                        currentNode.aClicks + 1, currentNode.bClicks, currentNode.price + 3, positionOnClickingA
                    )

                val positionOnClickingB =
                    Pair(currentNode.position.first + buttonB.first, currentNode.position.second + buttonB.second)
                val nodeOnClickingB =
                    Node(
                        currentNode.aClicks, currentNode.bClicks + 1, currentNode.price + 1, positionOnClickingB
                    )

                if (!nodesToCheck.containsKey(positionOnClickingA) || nodesToCheck[positionOnClickingA]!!.price > nodeOnClickingA.price)
                    nodesToCheckCopy[nodeOnClickingA.position] = nodeOnClickingA

                if (!nodesToCheck.containsKey(positionOnClickingB) || nodesToCheck[positionOnClickingB]!!.price > nodeOnClickingB.price)
                    nodesToCheckCopy[nodeOnClickingB.position] = nodeOnClickingB
            }
            nodesToCheck = nodesToCheckCopy
        }

        if (priceToFinish != Long.MAX_VALUE) priceToFinish else 0
    }
}

fun puzzle13dot1(): Long {
    val machines = lines.chunked(4)
    val buttonRegex = "X\\+(\\d+), Y\\+(\\d+)".toRegex()
    val targetRegex = "X=(\\d+), Y=(\\d+)".toRegex()

    return machines.sumOf {
        val buttonA = Pair(
            buttonRegex.find(it[0])!!.groups[1]!!.value.toLong(),
            buttonRegex.find(it[0])!!.groups[2]!!.value.toLong()
        )
        val buttonB = Pair(
            buttonRegex.find(it[1])!!.groups[1]!!.value.toLong(),
            buttonRegex.find(it[1])!!.groups[2]!!.value.toLong()
        )
        val target = Pair(
            targetRegex.find(it[2])!!.groups[1]!!.value.toLong() + 10000000000000,
            targetRegex.find(it[2])!!.groups[2]!!.value.toLong() + 10000000000000
        )

        val a =
            (buttonB.second * target.first - buttonB.first * target.second).toFloat() / (buttonA.second * buttonB.first - buttonB.second * buttonA.first).toFloat()

        val b =
            (buttonA.first * target.second - buttonA.second * target.first).toFloat() / (buttonA.first * buttonB.second - buttonA.second * buttonB.first).toFloat()

        if(a.rem(1) == 0.0.toFloat() && b.rem(1) == 0.0.toFloat()) 3 * a.toLong() + b.toLong() else 0
    }
}
