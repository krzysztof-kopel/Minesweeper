package minesweeper

fun Pair<Int, Int>.verify(boardSize: Int): Boolean = this.first in 0 until boardSize && this.second in 0 until boardSize

fun fisherYatesShuffle(lowerBound: Int, upperBound: Int, number: Int): List<Int> {
    val result = mutableListOf<Int>()
    val drawingPool = (lowerBound..upperBound).toMutableList()
    repeat(number) {
        val randomIndex = drawingPool.indices.random()
        result.add(drawingPool[randomIndex])
        if (randomIndex != drawingPool.size - 1) {
            drawingPool[randomIndex] = drawingPool.removeLast()
        } else {
            drawingPool.removeLast()
        }
    }
    return result
}

fun <T> MutableSet<T>.toggle(element: T) {
    if (!this.add(element)) {
        this.remove(element)
    }
}
