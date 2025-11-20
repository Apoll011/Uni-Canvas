package com.example

import pt.isel.canvas.*

data class GameConfig(val hero: Character, val bot: Character, val cellOnX: Int, val cellOnY: Int, val cellSize: Int, val drawGrid: Boolean, val drawTiming: Int, val animateTiming: Int, val aiTiming: Int, val garbageImage: String, val background: String)

class Game (val config: GameConfig) {
    val arena: Arena = Arena(config.cellOnX, config.cellOnY, config.cellSize)
    val hero: Character = config.hero
    var bots: List<Character> = listOf()
    var aiEngine: AIEngine = AIEngine(arena)
    var enemiesNumber: Int = (3..arena.gridsX).random()
    var obstacles: List<Cell> = listOf()

    init {
        hero.position = Cell.getCell(GRID_WIDTH * CELL_SIZE / 2, GRID_HEIGHT * CELL_SIZE / 2)

        repeat (enemiesNumber) {
            spawnBot()
        }
    }
}

fun Game.getForbiddenCells() : List<Cell> {
    return bots.map { it.position } + obstacles
}

fun Game.drawTrash(canvas: Canvas, cell: Cell) {
    val pos = cell.getCoordinate()
    canvas.drawImage(config.garbageImage, pos.first, pos.second, config.cellSize, config.cellSize)
}

fun Game.getRandomAvailableCell(): Cell {
    val forbidden = getForbiddenCells() + hero.position
    val center = Cell(config.cellOnX / 2, config.cellOnY / 2)

    var cell: Cell
    do {
        cell = Cell(
            (0 until config.cellOnX).random(),
            (0 until config.cellOnY).random()
        )

        if (!cell.canMove(forbidden)) continue

        val tooCloseToBot = bots.any { cell.pythagoreanDistance(it.position) < 2 }
        if (tooCloseToBot) continue

        break
    } while (true)

    return cell
}

fun Game.spawnBot() {
    val newBot = config.bot.copy()
    newBot.position = getRandomAvailableCell()
    bots = bots + newBot
}

fun Game.draw(canvas: Canvas) {
    canvas.erase()
    canvas.drawImage(config.background, 0, 0, canvas.width, canvas.height)
    if (config.drawGrid) {
        arena.draw(canvas)
    }
    hero.draw(canvas)

    for (bot in bots) {
        bot.draw(canvas)
    }

    for (trash in obstacles) {
        drawTrash(canvas, trash)
    }
}

fun Game.animate() {
    hero.nextAnimation()
    for (bot in bots) {
        bot.nextAnimation()
    }
}

fun Game.runEngine() {
    bots = aiEngine.computeNextState(this)
    calculateBotsCollision()
}


//Uhhhhhhhhh Please don´t Ask.... THIS SHOULD NEVER BE TOUCHED
private fun Game.calculateBotsCollision() {
    bots = bots.filter {
        (bots - it).none { bot ->
            if(bot.position.equalsCell(it.position))
                obstacles += it.position
            bot.position.equalsCell(it.position)
        }
    }
}

fun Game.onInput(code: KeyEvent) {
    val dir = getDir(code.char)
    if (dir != null) {
        hero.move(dir, getForbiddenCells())
    }
}