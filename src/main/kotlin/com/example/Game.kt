package com.example

import pt.isel.canvas.*

class Game (val arena: Arena, val hero: Character)

fun Game.draw(canvas: Canvas) {
    canvas.erase()
    arena.draw(canvas)
    hero.draw(canvas)
}

fun Game.animate() {
    hero.nextAnimation()
}

fun Game.onInput(code: KeyEvent) {
    val dir = getDir(code.char)
    if (dir != null) {
        hero.move(dir)
    }
}