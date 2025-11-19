package com.example

import kotlin.math.floor

const val CELL_SIZE = 128
const val GRID_WIDTH = 5
const val GRID_HEIGHT = 3

class Cell(val col:Int, val row:Int) {
    companion object
}

/**
 * Get the Cell given the coordinates.
 * @receiver The X and Y coordinates.
 */
fun Cell.Companion.getCell(x: Int, y: Int) : Cell {
    return Cell(
        col = floor((x / CELL_SIZE).toDouble()).toInt(),
        row = floor((y / CELL_SIZE).toDouble()).toInt()
    )
}

/**
 * Get the Coordinates given the Cell.
 * @receiver The Cell.
 */
fun Cell.getCoordinateCenter() : Pair<Int, Int> {
    return Pair(col * CELL_SIZE + CELL_SIZE / 2, row * CELL_SIZE + CELL_SIZE / 2)
}

fun Cell.getCoordinate() : Pair<Int, Int> {
    return Pair(col * CELL_SIZE, row * CELL_SIZE)
}

fun Cell.canMove(forbidden: List<Cell>?) : Boolean {
    forbidden?.forEach { t -> if(t.row == row && t.col == col) return false }
    return row in 0 until GRID_HEIGHT && col in 0 until GRID_WIDTH
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