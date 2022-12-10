package io.auroria.adventofcode

object Day10 {
    fun cathodeRayTube() {
        val data = this::class.java.getResourceAsStream("/day10/input")!!
            .bufferedReader()
            .readLines()

        val expandedCommandList = data.flatMap {
            if (it.startsWith("addx")) {
                val elementToAdd = it.removePrefix("addx").trim().toInt()
                listOf(0, elementToAdd)
            } else listOf(0)
        }

        val register = expandedCommandList.runningFold(1, Int::plus)

        println("Part 1 - Signal Strength: " + (20..220 step 40).sumOf { (register[it - 1]) * it })

        val image = register.chunked(40)
            .joinToString("\n") {
                it.withIndex().joinToString("") { (index, value) ->
                    if (value in listOf(index - 1, index, index + 1)) "█" else "."
                }
            }

        println("Part 2")
        print(image)
    }
}


fun main() {
    Day10.cathodeRayTube()
}