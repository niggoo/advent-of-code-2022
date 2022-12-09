package io.auroria.adventofcode

import kotlin.math.absoluteValue
import kotlin.math.sign

object Day9 {

    data class Position(val x: Int, val y: Int) {
        operator fun plus(other: Position) = Position(x + other.x, y + other.y)
    }

    enum class DirectionUpdate(val posDelta: Position) {
        R(Position(1, 0)),
        L(Position(-1, 0)),
        U(Position(0, 1)),
        D(Position(0, -1))
    }

    fun ropeBridge() {
        val data = this::class.java.getResourceAsStream("/day9/input")!!
            .bufferedReader()
            .readLines()

        val commands = data
            .map { it.split(" ") }
            .flatMap { command ->
                (0 until command.last().toInt()).map {
                    DirectionUpdate.valueOf(command.first())
                }
            }


        val headPositions = commands.runningFold(Position(0, 0)) { curr, next ->
            curr + next.posDelta
        }

        val tailPositionsPart1 = followPositions(headPositions)
        println("Part 1 - #TailPositions: " + tailPositionsPart1.toSet().count())

        val knots = (1..9).map { it.toString() }
        val tailPositionsPart2 = knots.fold(headPositions) { knotBeforePositions, _ ->
            followPositions(knotBeforePositions)
        }

        println("Part 2 - #TailPositions: " + tailPositionsPart2.toSet().count())
    }

    private fun followPositions(headPositions: List<Position>) =
        headPositions.runningFold(Position(0, 0)) { currTailPos, headPos ->
            val deltaX = headPos.x - currTailPos.x
            val deltaY = headPos.y - currTailPos.y

            val tailX = if (deltaX.absoluteValue >= deltaY.absoluteValue)
                headPos.x - deltaX.sign
            else headPos.x

            val tailY = if (deltaX.absoluteValue <= deltaY.absoluteValue)
                headPos.y - deltaY.sign
            else headPos.y

            Position(tailX, tailY)
        }
}

fun main() {
    Day9.ropeBridge()
}