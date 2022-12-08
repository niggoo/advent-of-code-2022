package io.auroria.adventofcode

object Day8 {

    fun <T> List<List<T>>.transpose(): List<List<T>> {
        val n = this.maxOfOrNull { it.size }!!

        val iterList = this.map { it.iterator() }

        return (0 until n)
            .map {
                iterList
                    .filter { it.hasNext() }
                    .map { it.next() }
            }
    }


    fun treetopTreeHouse() {
        val data = this::class.java.getResourceAsStream("/day8/input")!!
            .bufferedReader()
            .readLines()
            .map { line ->
                line.split("")
                    .filter { it.isNotEmpty() }
                    .map { treeHeight -> treeHeight.toInt() }
            }
        val leftRightVisible = findLeftRightVisibility(data)
        val upDownVisible = findLeftRightVisibility(data.transpose()).transpose()

        val overallVisibility = data.mapIndexed { i, _ ->
            data[i].mapIndexed { j, _ ->
                if (leftRightVisible[i][j] || upDownVisible[i][j]) 1 else 0
            }
        }
        println("Part 1 - Visible outside trees: " + overallVisibility.sumOf { it.sum() })
    }

    private fun findLeftRightVisibility(data: List<List<Int>>) =
        data.mapIndexed { i, treeHeights ->
            treeHeights.mapIndexed { j, treeHeight ->
                val visibleLeft = data[i].subList(0, j).all { it < treeHeight }
                val visibleRight = data[i].subList(j + 1, data[i].count()).all { it < treeHeight }
                (visibleLeft || visibleRight)
            }
        }
}

fun main() {
    Day8.treetopTreeHouse()
}