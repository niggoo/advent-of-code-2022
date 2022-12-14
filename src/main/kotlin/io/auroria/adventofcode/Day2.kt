package io.auroria.adventofcode

object Day2Task1 {
    enum class RPSChoice(val score: Int) {
        Rock(1),
        Paper(2),
        Scissor(3);

        companion object {
            fun of(charRepresentation: String) = when (charRepresentation) {
                "A", "X" -> Rock
                "B", "Y" -> Paper
                "C", "Z" -> Scissor
                else -> throw RuntimeException("Bad input to convert to RPSChoice $charRepresentation")
            }
        }
    }

    enum class RPSMatchOutcome(val score: Int) {
        Win(6),
        Draw(3),
        Loss(0)
    }


    fun rsp() {
        val data = this::class.java.getResourceAsStream("/day2/input")!!
            .bufferedReader()
            .readLines()


        val followedStrategy = data.filter { it.trim().isNotEmpty() }
            .map {
                val tuple = it.split(" ")
                Pair(RPSChoice.of(tuple.first()), RPSChoice.of(tuple.last()))
            }.map {
                val outcome = decideOutcome(it.first, it.second)
                outcome.score + it.second.score
            }

        println("Total score: ${followedStrategy.sum()}")
    }

    private fun decideOutcome(opponentChoice: RPSChoice, myChoice: RPSChoice) =
        when (opponentChoice) {
            RPSChoice.Rock -> {
                when (myChoice) {
                    RPSChoice.Rock -> RPSMatchOutcome.Draw
                    RPSChoice.Paper -> RPSMatchOutcome.Win
                    RPSChoice.Scissor -> RPSMatchOutcome.Loss
                }
            }

            RPSChoice.Paper -> {
                when (myChoice) {
                    RPSChoice.Rock -> RPSMatchOutcome.Loss
                    RPSChoice.Paper -> RPSMatchOutcome.Draw
                    RPSChoice.Scissor -> RPSMatchOutcome.Win
                }
            }

            RPSChoice.Scissor -> {
                when (myChoice) {
                    RPSChoice.Rock -> RPSMatchOutcome.Win
                    RPSChoice.Paper -> RPSMatchOutcome.Loss
                    RPSChoice.Scissor -> RPSMatchOutcome.Draw
                }
            }
        }
}

object Day2Task2 {

    enum class RPSDesiredOutcome(val score: Int) {
        Loss(0),
        Win(6),
        Draw(3);

        companion object {
            fun of(charRepresentation: String) = when (charRepresentation) {
                "X" -> Loss
                "Y" -> Draw
                "Z" -> Win
                else -> throw RuntimeException("Bad input to convert to RPSChoice $charRepresentation")
            }
        }
    }

    enum class RPSChoice(val score: Int) {
        Rock(1),
        Paper(2),
        Scissor(3);

        companion object {
            fun of(charRepresentation: String) = when (charRepresentation) {
                "A" -> Rock
                "B" -> Paper
                "C" -> Scissor
                else -> throw RuntimeException("Bad input to convert to RPSChoice $charRepresentation")
            }
        }
    }


    fun rsp() {
        val data = this::class.java.getResourceAsStream("/day2/input")!!
            .bufferedReader()
            .readLines()

        val followedNewStrategy = data
            .filter { it.trim().isNotEmpty() }
            .map {
                val tuple = it.split(" ")
                Pair(RPSChoice.of(tuple.first()), RPSDesiredOutcome.of(tuple.last()))
            }.map {
                val myChoice = decideChoice(it.first, it.second)
                it.second.score + myChoice.score
            }

        println("New Total score: ${followedNewStrategy.sum()}")

    }

    private fun decideChoice(opponentChoice: RPSChoice, desiredOutcome: RPSDesiredOutcome): RPSChoice {
        return when (desiredOutcome) {
            RPSDesiredOutcome.Loss -> {
                when (opponentChoice) {
                    RPSChoice.Rock -> RPSChoice.Scissor
                    RPSChoice.Paper -> RPSChoice.Rock
                    RPSChoice.Scissor -> RPSChoice.Paper
                }
            }

            RPSDesiredOutcome.Win -> {
                when (opponentChoice) {
                    RPSChoice.Rock -> RPSChoice.Paper
                    RPSChoice.Paper -> RPSChoice.Scissor
                    RPSChoice.Scissor -> RPSChoice.Rock
                }
            }

            RPSDesiredOutcome.Draw -> opponentChoice
        }
    }
}

fun main() {
    Day2Task1.rsp()
    Day2Task2.rsp()
}