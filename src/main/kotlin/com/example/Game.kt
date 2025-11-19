package com.example

import pt.isel.canvas.*

class Game (val arena: Arena, val hero: Hero)

fun Game.draw(canvas: Canvas) {
    canvas.erase()
    arena.draw(canvas)
    hero.draw(canvas)
}

fun Game.animate() {
    hero.nextAnimation()
}

fun Game.getDir(char: Char) : Direction? {
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

fun Game.onInput(code: KeyEvent) {
    val dir = getDir(code.char)
    if (dir != null) {
        hero.move(dir)
    }
}