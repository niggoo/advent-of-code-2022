package io.auroria.adventofcode

object Day5 {

    private fun <T> transpose(list: List<List<T>>): List<List<T>> {
        val n = list.maxOfOrNull { it.size }!!

        val iterList = list.map { it.iterator() }

        return (0 until n)
            .map {
                iterList
                    .filter { it.hasNext() }
                    .map { it.next() }
            }
    }

    fun supplyStacks() {
        val stackListPart1 = loadStack()
        val stackListPart2 = loadStack()
        val moves = loadMoves()


        moves.forEach { move ->
            repeat(move.first) {
                val removed = stackListPart1[move.second].removeLast()
                stackListPart1[move.third].addLast(removed)
            }
        }

        moves.forEach { move ->
            (1..move.first).map {
                stackListPart2[move.second].removeLast()
            }.asReversed().forEach { removed ->
                stackListPart2[move.third].addLast(removed)
            }
        }

        println("Part 1 - Creates on top: ${stackListPart1.joinToString("") { it.last() }}")
        println("Part 2 - Creates on top: ${stackListPart2.joinToString("") { it.last() }}")
    }

    private fun loadStack(): List<ArrayDeque<String>> {
        val data = this::class.java.getResourceAsStream("/day5/input")!!
            .bufferedReader()
            .readLines()

        val startData = data.filter { line -> line.contains("[A-Z]+".toRegex()) }


        val reversedSingleParts = startData.asReversed()
            .map { it.toList() }
        val transposedData = transpose(reversedSingleParts)
            .map { it.joinToString("").trim() }
            .filter { it.contains("[A-Z]+".toRegex()) }
            .map { itemStr -> itemStr.split("").filter { it.isNotEmpty() } }

        return transposedData.map { ArrayDeque(it) }
    }

    private fun loadMoves(): List<Triple<Int, Int, Int>> {
        val data = this::class.java.getResourceAsStream("/day5/input")!!
            .bufferedReader()
            .readLines()

        val moveData = data.filter { line -> line.startsWith("move ") }
        val moves = moveData.map { str ->
            val matchResults = Regex("move\\s(\\d+)\\sfrom\\s(\\d+)\\sto\\s(\\d+)")
                .matchEntire(str)!!.groupValues
                .slice(1..3)
                .map { it.toInt() }

            Triple(matchResults[0], matchResults[1] - 1, matchResults[2] - 1)
        }

        return moves
    }
}

fun main() {
    Day5.supplyStacks()
}