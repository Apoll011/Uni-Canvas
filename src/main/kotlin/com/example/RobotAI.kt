package com.example

import kotlin.collections.iterator
import kotlin.math.abs

class AIEngine(
    private val arena: Arena,
) {

    fun computeNextState(game: Game): Pair<List<Character>, List<Cell>> {

        val heroPos = game.hero.position
        val bots = game.bots.toMutableList()
        val obstacles = game.obstacles.toMutableList()

        val finalPositions = mutableMapOf<Cell, MutableList<Character>>()

        for (bot in bots) {

            val direction = computeNextStep(
                bot.position,
                heroPos,
                obstacles,
                bots.map { it.position }
            )

            val newPos = when(direction) {
                Direction.UP    -> Cell(bot.position.x,     bot.position.y - 1)
                Direction.DOWN  -> Cell(bot.position.x,     bot.position.y + 1)
                Direction.LEFT  -> Cell(bot.position.x - 1, bot.position.y)
                Direction.RIGHT -> Cell(bot.position.x + 1, bot.position.y)
                else -> bot.position
            }

            if (direction != null) bot.move(direction, obstacles)

            finalPositions.getOrPut(newPos) { mutableListOf() }.add(bot)
        }

        val survivors = mutableListOf<Character>()
        val newObstacles = mutableListOf<Cell>()

        for ((cell, chars) in finalPositions) {
            if (chars.size > 1) {
                newObstacles += cell
            } else {
                survivors += chars[0]
            }
        }

        obstacles += newObstacles

        return survivors to obstacles
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

        val preferred = mutableListOf<Direction>()

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
