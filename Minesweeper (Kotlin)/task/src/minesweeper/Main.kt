package minesweeper

import kotlin.system.exitProcess

// Output works well only for boardSize < 10
const val boardSize = 9

fun main() {

    print("How many mines do you want on the field? ")
    val numberOfMines = readln().toInt()

    val field = Minefield(boardSize, numberOfMines)
    field.markFreeFields()
    println(field)

    while (!field.isGameFinished()) {
        print("Set/unset mines marks or claim a cell as free: ")
        val userInput = readln().split(" ")
        val fieldToMark = Pair(userInput[0].toInt() - 1, userInput[1].toInt() - 1)
        when (userInput[2]) {
            "free" -> {
                if (!field.discoverFields(fieldToMark.first, fieldToMark.second)) {
                    field.makeMinesVisible()
                    println(field)
                    println("You stepped on a mine and failed!")
                    exitProcess(0)
                }
            }
            "mine" -> {
                field.changeFieldMarking(fieldToMark.first, fieldToMark.second)
            }
            "show" -> {
                field.makeMinesVisible()
            }
        }

        println(field)
    }
    println("Congratulations! You found all the mines!")
}

