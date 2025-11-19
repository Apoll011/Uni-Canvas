package com.example

import kotlin.math.abs

class AIEngine(
    private val arena: Arena,
) {

    fun computeNextState(game: Game): List<Character> {

        val heroPos = game.hero.position
        val bots = game.bots
        val obstacles = game.obstacles

        for (bot in bots) {

            val direction = computeNextStep(
                bot.position,
                heroPos,
                obstacles,
                bots.map { it.position }
            )

            if (direction != null) bot.move(direction, game.obstacles)
        }

        return bots
    }

    private fun computeNextStep(
        start: Cell,
        goal: Cell,
        obstacles: List<Cell>,
        bots: List<Cell>
    ): Direction? {

        if (start == goal) return null

        val blocked = (obstacles + bots).toSet()

        fun free(x: Int, y: Int): Boolean =
            x in 0 until arena.gridsX &&
                    y in 0 until arena.gridsY &&
                    Cell(x, y) !in blocked

        val dx = goal.x - start.x
        val dy = goal.y - start.y

        val preferred: MutableList<Direction> = mutableListOf()

        if (abs(dx) > abs(dy)) {
            preferred += if (dx > 0) Direction.RIGHT else Direction.LEFT
            preferred += if (dy > 0) Direction.DOWN else Direction.UP
        } else {
            preferred += if (dy > 0) Direction.DOWN else Direction.UP
            preferred += if (dx > 0) Direction.RIGHT else Direction.LEFT
        }

        val fallback = listOf(
            Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT
        )

        for (d in preferred) {
            val c = start.nextCell(d)
            if (free(c.x, c.y)) return d
        }

        for (d in fallback) {
            val c = start.nextCell(d)
            if (free(c.x, c.y)) return d
        }

        return null
    }
}
