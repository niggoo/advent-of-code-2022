package io.auroria.adventofcode

object Day4 {
    fun campCleanup() {
        val data = this::class.java.getResourceAsStream("/day4/input")!!
            .bufferedReader()
            .readLines()

        val mappedDataPairs = data.map { pairStr ->
            val pairs = pairStr
                .split(",")
                .map {
                    val startEndRange = it.split("-")
                    IntRange(startEndRange.first().toInt(), startEndRange.last().toInt())
                }

            Pair(pairs.first(), pairs.last())
        }

        val overlappingAssignments = mappedDataPairs.filter { pair ->
            (pair.first.start <= pair.second.start
                    && pair.first.endInclusive >= pair.second.endInclusive)
                    || (pair.second.start <= pair.first.start
                    && pair.second.endInclusive >= pair.first.endInclusive)
        }

        val countOverlappingAssignmentBlocks = mappedDataPairs.filter { pair ->
            pair.first.toList()
                .intersect(pair.second.toList())
                .isNotEmpty()
        }

        println("Overlapping assignment count: ${overlappingAssignments.count()}")
        println("Overlapping assignment block count sum: ${countOverlappingAssignmentBlocks.count()}")
    }

}

fun main() {
    Day4.campCleanup()
}