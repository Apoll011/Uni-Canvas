package com.example

import pt.isel.canvas.*

fun main() {
    onStart {
        val canvas = Canvas(GRID_WIDTH * CELL_SIZE, GRID_HEIGHT * CELL_SIZE, WHITE)

        val arena = Arena(GRID_WIDTH, GRID_HEIGHT, CELL_SIZE)
        val hero = Hero()

        val game = Game(arena, hero)

        canvas.onTimeProgress(10) {
            game.draw(canvas)
        }
a
        canvas.onTimeProgress(100) {
            game.animate()
        }

        canvas.onKeyPressed { code ->
            game.onInput(code)
        }
    }
    onFinish {  }
}