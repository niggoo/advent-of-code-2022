package io.auroria.adventofcode

object Day3 {
    fun rucksackReorganization() {
        val data = this::class.java.getResourceAsStream("/day3/input")!!
            .bufferedReader()
            .readLines()

        val compartmentPairs = data.map {
            Pair(
                it.substring(0..it.length / 2), // first compartment
                it.substring(it.length / 2, it.length) // second compartment
            )
        }
        val intersectedItems = compartmentPairs.map { rucksackCompartments ->
            rucksackCompartments.first
                .toCharArray()
                .intersect(rucksackCompartments.second.asIterable())
                .first()
        }

        val priorities = intersectedItems.map { item ->
            calculateItemPriority(item)
        }

        println("Sum of priorites : ${priorities.sum()}")
    }

    fun rucksackBadges() {
        val data = this::class.java.getResourceAsStream("/day3/input")!!
            .bufferedReader()
            .readLines()

        val elveBadgeTriples = data
            .windowed(3, 3)
            .map {
                it[0].toCharArray()
                    .intersect(it[1].asIterable())
                    .intersect(it[2].asIterable())
                    .first()
            }
        val priorities = elveBadgeTriples
            .map {
                calculateItemPriority(it)
            }

        println("Sum of badge priorities: ${priorities.sum()}")

    }

    private fun calculateItemPriority(item: Char) = if (item.code >= 'A'.code && item.code <= 'Z'.code) {
        item.code - 'A'.code + 27
    } else if (item.code >= 'a'.code && item.code <= 'z'.code) {
        item.code - 'a'.code + 1
    } else {
        throw RuntimeException("Not a valid char")
    }
}

fun main() {
    Day3.rucksackReorganization()
    Day3.rucksackBadges()
}