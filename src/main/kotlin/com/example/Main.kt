package com.example

import pt.isel.canvas.*

fun main() {
    onStart {
        val canvas = Canvas(GRID_WIDTH * CELL_SIZE, GRID_HEIGHT * CELL_SIZE, WHITE)

        val config = GameConfig(
            Character("hero", 48, 3, 100, 16 to 16),
            Character("robot", 64, 4, 128, -4 to -10),
            GRID_WIDTH,
            GRID_HEIGHT,
            CELL_SIZE,
            true,
            10,
            100,
            1000,
            "garbage"
        )

        val game = Game(config)

        canvas.onTimeProgress(config.drawTiming) {
            game.draw(canvas)
        }

        canvas.onTimeProgress(config.animateTiming) {
            game.animate()
        }

        canvas.onTimeProgress(config.aiTiming) {
            game.runEngine()
        }

        canvas.onKeyPressed { code ->
            game.onInput(code)
        }
    }
    onFinish {  }
}