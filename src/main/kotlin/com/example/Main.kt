package com.example

import pt.isel.canvas.*

fun main() {
    onStart {
        val canvas = Canvas(GRID_WIDTH * CELL_SIZE, GRID_HEIGHT * CELL_SIZE, WHITE)

        val arena = Arena(GRID_WIDTH, GRID_HEIGHT, CELL_SIZE)
        val hero = Character("hero", Cell.getCell(GRID_WIDTH * CELL_SIZE / 2, GRID_HEIGHT * CELL_SIZE / 2), 48, 3)

        val game = Game(arena, hero, listOf())

        canvas.onTimeProgress(10) {
            game.draw(canvas)
        }

        canvas.onTimeProgress(100) {
            game.animate()
        }

        canvas.onTimeProgress(1000) {
            game.runEngine()
        }

        canvas.onKeyPressed { code ->
            game.onInput(code)
        }
    }
    onFinish {  }
}