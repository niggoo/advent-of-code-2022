package io.auroria.adventofcode

object Day1 {
    fun elveCalories() {
        // Load data
        val data = this::class.java.getResourceAsStream("/day1/input1")!!
            .bufferedReader()
            .readLines()

        // Get Empty line indices
        val emptyLineIndices = data.indices
            .filter { index -> data[index].trim().isEmpty() }

        // Enrich with beginning and end indices
        // create pairs for data boundaries
        val indexTuples = listOf(-1).plus(emptyLineIndices).plus(data.count())
            .windowed(2, 1)
            .map { indexPair -> Pair(indexPair.first() + 1, indexPair.last()) }

        // Use pairs to fetch range in list, convert to int and sum
        val sums = indexTuples.map {
            data.subList(it.first, it.second)
                .map { it.toInt() }
                .sum()
        }

        println(sums.max())
    }
}

fun main(args: Array<String>) {
    Day1.elveCalories()
}