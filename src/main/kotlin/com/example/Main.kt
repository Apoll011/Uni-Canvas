package com.example

import pt.isel.canvas.*

fun main() {
    onStart {
        val canvas = Canvas(GRID_WIDTH * CELL_SIZE, GRID_HEIGHT * CELL_SIZE, WHITE)

        val config = GameConfig(
            GameState.RUNNING,
            Character("hero", 48, 3, 100, 16 to 16),
            Character("robot", 64, 4, 128, -4 to -10),
            listOf(Cell(0, 0), Cell(1, 0), Cell(7, 0), Cell(7, 4), Cell(0, 4), Cell(1, 4)),
            GRID_WIDTH,
            GRID_HEIGHT,
            CELL_SIZE,
            false,
            10,
            100,
            700,
            "garbage",
            "background2"
        )

        val game = Game(config)

        canvas.onTimeProgress(config.drawTiming) {
            game.draw(canvas)
        }

        canvas.onTimeProgress(config.animateTiming) {
            game.animate()
        }

        canvas.onKeyPressed { code ->
            if (game.config.state == GameState.RUNNING)
                game.onInput(code)
        }
    }
    onFinish {  }
}