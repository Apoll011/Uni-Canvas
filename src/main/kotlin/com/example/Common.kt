package com.example

import kotlin.math.floor

const val CELL_SIZE = 128
const val GRID_WIDTH = 8
const val GRID_HEIGHT = 6

class Cell(val x:Int, val y:Int) {
    companion object
}

/**
 * Get the Cell given the coordinates.
 * @receiver The X and Y coordinates.
 */
fun Cell.Companion.getCell(x: Int, y: Int) : Cell {
    return Cell(
        x = floor((x / CELL_SIZE).toDouble()).toInt(),
        y = floor((y / CELL_SIZE).toDouble()).toInt()
    )
}

/**
 * Get the Coordinates given the Cell.
 * @receiver The Cell.
 */
fun Cell.getCoordinate() : Pair<Int, Int> {
    return Pair(x * CELL_SIZE, y * CELL_SIZE)
}

fun Cell.canMove(forbidden: List<Cell>?) : Boolean {
    forbidden?.forEach { t -> if(t.equalsCell(this)) return false }
    return y in 0 until GRID_HEIGHT && x in 0 until GRID_WIDTH
}

fun Cell.equalsCell(other: Cell) : Boolean {
    return this.y == other.y && this.x == other.x
}


fun Cell.nextCell(d: Direction): Cell {
    return when(d) {
        Direction.UP    -> Cell(x, y - 1)
        Direction.DOWN  -> Cell(x, y + 1)
        Direction.LEFT  -> Cell(x - 1, y)
        Direction.RIGHT -> Cell(x + 1, y)

        Direction.DIAGONAL_DL -> Cell(x - 1, y - 1)
        Direction.DIAGONAL_UL -> Cell(x + 1, y - 1)
        Direction.DIAGONAL_DR -> Cell(x - 1, y + 1)
        Direction.DIAGONAL_UR -> Cell(x + 1, y + 1)
    }
}

fun Cell.distance(b: Cell): Int {
    val dx = x - b.x
    val dy = y - b.y
    return kotlin.math.sqrt((dx*dx + dy*dy).toDouble()).toInt()
}


enum class Direction {
    UP, DOWN, LEFT, RIGHT,
    DIAGONAL_DL, DIAGONAL_UL, DIAGONAL_DR, DIAGONAL_UR;
}

fun getDir(char: Char) : Direction? {
    return when (char) {
        'w' -> Direction.UP
        'a' -> Direction.LEFT
        's' -> Direction.DOWN
        'd' -> Direction.RIGHT

        'q' -> Direction.DIAGONAL_UL
        'e' -> Direction.DIAGONAL_UR
        'c' -> Direction.DIAGONAL_DR
        'z' -> Direction.DIAGONAL_DL
        else -> null
    }
}