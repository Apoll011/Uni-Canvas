package com.example

import pt.isel.canvas.Canvas

class Game (val arena: Arena, val hero: Hero)

fun Game.draw(canvas: Canvas) {
    canvas.erase()
    arena.draw(canvas)
    hero.draw(canvas)
}