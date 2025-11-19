package com.example

import pt.isel.canvas.*



class Hero () {
    var position: Cell = Cell.getCell(GRID_WIDTH * CELL_SIZE / 2, GRID_HEIGHT * CELL_SIZE / 2)
    var currentFrame: Int = 0
    var currentDirection: Direction = Direction.DOWN
}

fun Hero.nextAnimation() {
    currentFrame = (currentFrame + 1) % 3
}

fun Hero.getAnimation(): String {
    val frame = currentFrame
    val frameSize = 48

    val (baseColumn, row) = when (currentDirection) {
        Direction.DOWN        -> 0 to 0
        Direction.LEFT        -> 0 to 1
        Direction.RIGHT       -> 0 to 2
        Direction.UP          -> 0 to 3

        Direction.DIAGONAL_DL -> 3 to 0
        Direction.DIAGONAL_UL -> 3 to 1
        Direction.DIAGONAL_DR -> 3 to 2
        Direction.DIAGONAL_UR -> 3 to 3
    }

    val column = baseColumn + frame

    val pixelX = column * frameSize
    val pixelY = row * frameSize

    return "hero|$pixelX,$pixelY,48,48"
}

fun Hero.canMove(newCell: Cell) : Boolean {
    return newCell.row in 0 until GRID_HEIGHT && newCell.col in 0 until GRID_WIDTH
}

fun Hero.move(direction: Direction) {
    val newPosition = when (direction) {
        Direction.DOWN        -> Cell(position.col, position.row + 1)
        Direction.LEFT        -> Cell(position.col - 1, position.row)
        Direction.RIGHT       -> Cell(position.col + 1, position.row)
        Direction.UP          -> Cell(position.col, position.row - 1)

        Direction.DIAGONAL_DL -> Cell(position.col - 1, position.row + 1)
        Direction.DIAGONAL_UL -> Cell(position.col + 1, position.row + 1)
        Direction.DIAGONAL_UR -> Cell(position.col + 1, position.row - 1)
        Direction.DIAGONAL_DR -> Cell(position.col - 1, position.row - 1)
    }

    if (!canMove(newPosition)) return

    currentDirection = direction
    position = newPosition
}

fun Hero.draw(canvas: Canvas) {
    val pos = Cell.getCoordinate(position)
    canvas.drawImage(getAnimation(), pos.first, pos.second, CELL_SIZE, CELL_SIZE)
}