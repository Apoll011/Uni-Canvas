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

fun Game.onInput(code: KeyEvent) {
    when (code.char) {
        'w' -> hero.currentDirection = Direction.UP
        'a' -> hero.currentDirection = Direction.LEFT
        's' -> hero.currentDirection = Direction.DOWN
        'd' -> hero.currentDirection = Direction.RIGHT
    }
}