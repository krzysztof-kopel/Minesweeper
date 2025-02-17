package minesweeper

class Minefield(private val boardSize: Int, private val numberOfMines: Int) {
    private val mineField: List<List<Field>>
    private val markedFields: MutableSet<Field> = mutableSetOf()
    init {
        val tempField = MutableList(boardSize) { x ->
            MutableList(boardSize) { y -> Field(FieldType.FREE, x, y) }
        }
        fisherYatesShuffle(0, this.boardSize * this.boardSize - 1, this.numberOfMines)
            .forEach{tempField[it / boardSize][it % boardSize] = Field(FieldType.MINE, it / boardSize, it % boardSize)}
        this.mineField = tempField
    }

    override fun toString(): String {
        return " |${(1..this.boardSize).joinToString("")}|\n"
            .plus("—|${"—".repeat(this.boardSize)}|\n")
            .plus(this.mineField.withIndex().joinToString("\n") { (index, row) -> (index + 1).toString()
                .plus("|")
                .plus(row.joinToString(""))
                .plus("|")})
            .plus("\n")
            .plus("—|${"—".repeat(this.boardSize)}|")
    }

    fun markFreeFields() {
        val fieldsWithMines = this.mineField.flatten().filter{it.type == FieldType.MINE}

        for (field in fieldsWithMines) {
            for (neighborRelative in neighbors) {
                val neighborCoords = Pair(field.xCoord + neighborRelative.first, field.yCoord + neighborRelative.second)

                if (!neighborCoords.verify(this.boardSize)) {
                    continue
                }

                this.mineField[neighborCoords.first][neighborCoords.second].inc()
            }
        }
    }

    fun isGameFinished(): Boolean = (this.markedFields.size == this.numberOfMines && this.markedFields.all{it.type == FieldType.MINE})
            || this.mineField.flatten().filterNot{it.type == FieldType.MINE}.all{it.visible}

    fun changeFieldMarking(xCoord: Int, yCoord: Int) {
        this.mineField[yCoord][xCoord].mark()
        this.markedFields.toggle(this.mineField[yCoord][xCoord])
    }

    fun discoverFields(xCoord: Int, yCoord: Int, firstCall: Boolean = true): Boolean {
        val currentField = this.mineField[yCoord][xCoord]

        if (firstCall && currentField.type == FieldType.MINE) {
            return false
        } else if (currentField.type == FieldType.MINE || currentField.visible || currentField.hasMinesNearby()) {
            currentField.setVisible()
            return true
        }

        currentField.setVisible()

        for (coords in neighbors.map{Pair(xCoord + it.first, yCoord + it.second)}.filter{it.verify(this.boardSize)}) {
            this.discoverFields(coords.first, coords.second, false)
        }
        return true
    }

    fun makeMinesVisible() {
        this.mineField.flatten().filter{it.type == FieldType.MINE}.forEach{it.setVisible()}
    }

    companion object {
        val neighbors = listOf(-1, 0, 1).flatMap{x -> listOf(-1, 0, 1).map{y -> Pair(x, y)}}.filterNot{it == Pair(0, 0)}
    }
}