package com.example

import pt.isel.canvas.*



class Character (val sourceImg : String) {
    var position: Cell = Cell.getCell(GRID_WIDTH * CELL_SIZE / 2, GRID_HEIGHT * CELL_SIZE / 2)
    var currentFrame: Int = 0
    var currentDirection: Direction = Direction.DOWN
}

fun Character.nextAnimation() {
    currentFrame = (currentFrame + 1) % 3
}

fun Character.getAnimation(): String {
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

    val column = baseColumn + currentFrame

    val pixelX = column * frameSize
    val pixelY = row * frameSize

    return "$sourceImg|$pixelX,$pixelY,48,48"
}

fun Character.move(direction: Direction) {
    val newPositionMatrix = when (direction) {
        Direction.DOWN        -> 0 to 1
        Direction.LEFT        -> -1 to 0
        Direction.RIGHT       -> 1 to 0
        Direction.UP          -> 0 to -1

        Direction.DIAGONAL_DL -> -1 to 1
        Direction.DIAGONAL_UL -> -1 to -1
        Direction.DIAGONAL_UR -> 1 to -1
        Direction.DIAGONAL_DR -> 1 to 1
    }

    val newPosition = Cell(position.col + newPositionMatrix.first, position.row + newPositionMatrix.second)

    if (!newPosition.canMove()) return

    currentDirection = direction
    position = newPosition
}

fun Character.draw(canvas: Canvas) {
    val pos = Cell.getCoordinate(position)
    canvas.drawImage(getAnimation(), pos.first, pos.second, CELL_SIZE, CELL_SIZE)
}