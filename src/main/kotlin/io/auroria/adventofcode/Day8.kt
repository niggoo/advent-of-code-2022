package io.auroria.adventofcode

object Day8 {

    private fun <T> List<List<T>>.transpose(): List<List<T>> {
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
                    .filter { it.isNotBlank() }
                    .map { treeHeight -> treeHeight.toInt() }
            }
        val leftRightVisible = findLeftRightVisibility(data)
        val upDownVisible = findLeftRightVisibility(data.transpose()).transpose()
        val overallVisibility = findOverallVisibility(data, leftRightVisible, upDownVisible)

        println("Part 1 - Visible outside trees: " + overallVisibility.sumOf { it.sum() })


        val leftRightScenicTreeVisibility = findLeftRightScenicTreeVisibility(data)
        val upDownScenicTreeVisibility = findLeftRightScenicTreeVisibility(data.transpose()).transpose()

        val overallScenicTreeVisibility = List(data.size) { i ->
            List(data[i].size) { j ->
                leftRightScenicTreeVisibility[i][j] * upDownScenicTreeVisibility[i][j]
            }
        }

        println("Part 2 - Best scenic tree visibility: " + overallScenicTreeVisibility.maxBy { it.max() }.max())
    }

    private fun findLeftRightScenicTreeVisibility(data: List<List<Int>>) =
        data.mapIndexed { i, treeHeights ->
            treeHeights.mapIndexed { j, treeHeight ->
                val firstBlockingTreeLeftIndex = data[i].subList(0, j).asReversed().indexOfFirst { it >= treeHeight }
                val firstBlockingTreeRightIndex =
                    data[i].subList(j + 1, data[i].count()).indexOfFirst { it >= treeHeight }

                val visibleTreesLeft = if (firstBlockingTreeLeftIndex != -1)
                    firstBlockingTreeLeftIndex + 1
                else j

                val visibleTreesRight = if (firstBlockingTreeRightIndex != -1)
                    firstBlockingTreeRightIndex + 1
                else treeHeights.count() - 1 - j

                visibleTreesLeft * visibleTreesRight
            }
        }

    private fun findOverallVisibility(
        data: List<List<Int>>,
        leftRightVisible: List<List<Boolean>>,
        upDownVisible: List<List<Boolean>>
    ) = List(data.size) { i ->
        List(data[i].size) { j ->
            if (leftRightVisible[i][j] || upDownVisible[i][j]) 1 else 0
        }
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