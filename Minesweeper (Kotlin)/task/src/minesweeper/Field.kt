package minesweeper

class Field(val type: FieldType, val xCoord: Int, val yCoord: Int) {
    private var minesNearby: Int = 0
    private var marked: Boolean = false
    internal var visible: Boolean = false
        get() = field

    override fun toString(): String = when {
        this.visible && this.type == FieldType.MINE -> "X"
        this.marked -> "*"
        !this.visible -> "."
        else -> ( when (this.type) {
            FieldType.MINE -> "."
            FieldType.FREE -> if (this.minesNearby > 0) this.minesNearby.toString() else "/"
        })
    }

    fun mark() {
        this.marked = this.marked.not()
    }

    fun setVisible() {
        this.marked = false
        this.visible = true
    }

    fun hasMinesNearby(): Boolean = this.minesNearby > 0

    operator fun inc(): Field {
        this.minesNearby++
        return this
    }
}