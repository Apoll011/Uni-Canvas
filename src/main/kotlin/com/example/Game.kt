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

fun Game.getRandomAvailableCell(): Cell {
    val forbidden = getForbiddenCells() + hero.position
    var cell: Cell
    do {
        cell = Cell((0 until GRID_WIDTH).random(), (0 until GRID_HEIGHT).random())
    } while (!cell.canMove(forbidden))
    return cell
}

fun Game.spawnBot() {
    bots = bots + Character("robot", getRandomAvailableCell(), 64, 4)
}

fun Game.draw(canvas: Canvas) {
    canvas.erase()
    arena.draw(canvas)
    hero.draw(canvas)

    for (bot in bots) {
        bot.draw(canvas)
    }

    for (trash in obstacles) {
        drawTrash(canvas, trash)
    }
}

fun Game.animate() {
    hero.nextAnimation()
    for (bot in bots) {
        bot.nextAnimation()
    }
}

fun Game.onInput(code: KeyEvent) {
    val dir = getDir(code.char)
    if (dir != null) {
        hero.move(dir, getForbiddenCells())
    }
}