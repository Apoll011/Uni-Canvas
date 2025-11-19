package com.example

import kotlin.math.floor

const val CELL_SIZE = 128
const val GRID_WIDTH = 5
const val GRID_HEIGHT = 3

class Cell(val col:Int, val row:Int) {
    companion object
}

enum class Dir { LEFT, RIGHT, UP, DOWN }

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
fun Cell.Companion.getCoordinateCenter(cell: Cell) : Pair<Int, Int> {
    return Pair(cell.col * CELL_SIZE + CELL_SIZE / 2, cell.row * CELL_SIZE + CELL_SIZE / 2)
}

fun Cell.Companion.getCoordinate(cell: Cell) : Pair<Int, Int> {
    return Pair(cell.col * CELL_SIZE, cell.row * CELL_SIZE)
}

fun Cell.canMove() : Boolean {
    return row in 0 until GRID_HEIGHT && col in 0 until GRID_WIDTH
}

enum class Direction {
    UP, DOWN, LEFT, RIGHT,
    DIAGONAL_DL, DIAGONAL_UL, DIAGONAL_DR, DIAGONAL_UR;
}