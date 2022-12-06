package io.auroria.adventofcode

object Day6 {
    fun tuningTrouble() {
        val data = this::class.java.getResourceAsStream("/day6/input")!!
            .bufferedReader()
            .readLine()

        val startOfPacketIndex = data.windowed(4, 1)
            .indexOfFirst { it.toSet().count() == 4 } + 4

        val startOfMessageIndex = data.windowed(14, 1)
            .indexOfFirst { it.toSet().count() == 14 } + 14

        println("Part 1 - Start Of Packet Index: $startOfPacketIndex")
        println("Part 2 - Start Of Message Index: $startOfMessageIndex")
    }
}

fun main() {
    Day6.tuningTrouble()
}