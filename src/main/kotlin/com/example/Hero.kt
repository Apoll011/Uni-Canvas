package com.example

import pt.isel.canvas.*

class Hero () {
    var position: Cell = Cell.getCell(GRID_WIDTH * CELL_SIZE / 2, GRID_HEIGHT * CELL_SIZE / 2)
}

fun Hero.draw(canvas: Canvas) {
    val pos = Cell.getCoordinateCenter(position)
    canvas.drawCircle(pos.first, pos.second, CELL_SIZE / 2, BLACK)
}