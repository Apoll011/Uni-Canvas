package com.example

import pt.isel.canvas.*



class Character (val sourceImg : String, val frameSize: Int, val numberOfFrames: Int, val size: Int, val placementOffset: Pair<Int, Int> = 0 to 0) {
    lateinit var position: Cell
    var currentFrame: Int = 0
    var currentDirection: Direction = Direction.DOWN
    var timeSinceLastMoved = 0

    var drawX: Double = 0.0
    var drawY: Double = 0.0
    var drawInitialized: Boolean = false
}

fun Character.nextAnimation() {
    currentFrame = if (timeSinceLastMoved < 4)(currentFrame + 1) % numberOfFrames else 1
    timeSinceLastMoved++

    val target = position.getCoordinate()
    val targetX = (target.first + placementOffset.first).toDouble()
    val targetY = (target.second + placementOffset.second).toDouble()

    if (!drawInitialized) {
        drawX = targetX
        drawY = targetY
        drawInitialized = true
        return
    }

    val step = (CELL_SIZE / 4.0)

    val dx = targetX - drawX
    val dy = targetY - drawY

    if (kotlin.math.abs(dx) <= step) drawX = targetX else drawX += kotlin.math.sign(dx) * step
    if (kotlin.math.abs(dy) <= step) drawY = targetY else drawY += kotlin.math.sign(dy) * step
}

fun Character.getAnimation(): String {
    val (baseColumn, row) = when (currentDirection) {
        Direction.DOWN        -> 0 to 0
        Direction.LEFT        -> 0 to 1
        Direction.RIGHT       -> 0 to 2
        Direction.UP          -> 0 to 3

        Direction.DIAGONAL_DL -> 3 to 0
        Direction.DIAGONAL_UL -> 3 to 1
        Direction.DIAGONAL_DR -> 3 to 2
        Direction.DIAGONAL_UR -> 3 to 3
    }

    val column = baseColumn + currentFrame

    val pixelX = column * frameSize
    val pixelY = row * frameSize

    return "$sourceImg|$pixelX,$pixelY,$frameSize,$frameSize"
}

fun Character.move(direction: Direction, forbidden: List<Cell>) {
    currentDirection = direction
    val newPositionMatrix = when (direction) {
        Direction.DOWN        -> 0 to 1
        Direction.LEFT        -> -1 to 0
        Direction.RIGHT       -> 1 to 0
        Direction.UP          -> 0 to -1

        Direction.DIAGONAL_DL -> -1 to 1
        Direction.DIAGONAL_UL -> -1 to -1
        Direction.DIAGONAL_UR -> 1 to -1
        Direction.DIAGONAL_DR -> 1 to 1
    }

    val newPosition = Cell(position.x + newPositionMatrix.first, position.y + newPositionMatrix.second)

    if (!newPosition.canMove(forbidden)) return

    position = newPosition
    timeSinceLastMoved = 0
}

fun Character.draw(canvas: Canvas) {
    if (!drawInitialized) {
        val target = position.getCoordinate()
        drawX = (target.first + placementOffset.first).toDouble()
        drawY = (target.second + placementOffset.second).toDouble()
        drawInitialized = true
    }

    val x = drawX.toInt()
    val y = drawY.toInt()
    canvas.drawImage(getAnimation(), x, y, size, size)
}

fun Character.copy(): Character {
    return Character(sourceImg, frameSize, numberOfFrames, size, placementOffset)
}