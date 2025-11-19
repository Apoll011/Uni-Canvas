package com.example

import pt.isel.canvas.*

class Game (val arena: Arena, val hero: Character, var obstacles: List<Cell>) {
    var bots: List<Character> = listOf()
}

fun Game.getForbiddenCells() : List<Cell> {
    return bots.map { it.position } + obstacles
}

fun Game.drawTrash(canvas: Canvas, cell: Cell) {
    val pos = cell.getCoordinate()
    canvas.drawImage("garbage", pos.first, pos.second, CELL_SIZE, CELL_SIZE)
}

fun Game.draw(canvas: Canvas) {
    canvas.erase()
    arena.draw(canvas)
    hero.draw(canvas)

    for (trash in obstacles) {
        drawTrash(canvas, trash)
    }
}

fun Game.animate() {
    hero.nextAnimation()
}

fun Game.onInput(code: KeyEvent) {
    val dir = getDir(code.char)
    if (dir != null) {
        hero.move(dir, getForbiddenCells())
    }
}