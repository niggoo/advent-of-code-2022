package io.auroria.adventofcode


object Day5 {

    private fun <T> transpose(list: List<List<T>>): List<List<T>> {
        val N = list.map { it.size }.max()

        val iterList = list.map { it.iterator() }

        return (0..N - 1)
            .map {
                iterList
                    .filter { it.hasNext() }
                    .map { m -> m.next() }
            }
    }

    fun supplyStacks() {
        val data = this::class.java.getResourceAsStream("/day5/input")!!
            .bufferedReader()
            .readLines()

        val startData = data.filter { line -> line.contains("[A-Z]+".toRegex()) }
        val moveData = data.filter { line -> line.startsWith("move ") }

        val reversedSingleParts = startData.asReversed()
            .map { it.toList() }
        val transposedData = transpose(reversedSingleParts)
            .map { it.joinToString("").trim() }
            .filter { it.contains("[A-Z]+".toRegex()) }
            .map { it.split("").filter { it.isNotEmpty() } }

        val stackList = transposedData.map { ArrayDeque(it) }

        val moves = moveData.map { str ->
            val matchResults = Regex("move\\s(\\d+)\\sfrom\\s(\\d+)\\sto\\s(\\d+)")
                .matchEntire(str)!!.groupValues
                .slice(1..3)
                .map { it.toInt() }


            Triple(matchResults[0], matchResults[1] - 1, matchResults[2] - 1)
        }


        moves.forEach { move ->
            repeat(move.first) {
                val removed = stackList[move.second].removeLast()
                stackList[move.third].addLast(removed)
            }
        }

        println("Creates on top: ${stackList.map { it.last() }.joinToString("")}")
    }
}

fun main() {
    Day5.supplyStacks()
}