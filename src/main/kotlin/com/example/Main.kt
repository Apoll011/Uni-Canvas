package com.example

import pt.isel.canvas.*
import kotlin.collections.mapIndexed

const val BOARD_SIZE = 500;
const val COLS_PER_ROW = 3;
const val CELL_SIZE = BOARD_SIZE / COLS_PER_ROW;

fun grid(canvas: Canvas) {
    canvas.drawLine(CELL_SIZE, 0, CELL_SIZE, canvas.height)
    canvas.drawLine(CELL_SIZE*2, 0, CELL_SIZE*2, canvas.height)
    canvas.drawLine(0, CELL_SIZE, canvas.width, CELL_SIZE)
    canvas.drawLine(0, CELL_SIZE*2, canvas.width, CELL_SIZE*2)
}

fun getCell(x: Int, y: Int, canvas: Canvas): Int {
    val w_s = canvas.width/3
    val h_s = canvas.height/3

    val pos_x = if (x < w_s) 0 else if (x < w_s*2) 1 else 2
    val pos_y = if (y < h_s) 0 else if (y < h_s*2) 1 else 2

    return pos_x + pos_y * 3
}

fun registerGameMove(game: List<Char>, indice: Int, player: Char) : List<Char> {
    return game.mapIndexed { i, existing ->  if (i == indice) player else existing }
}

fun drawIcon(x: Int, y: Int, player: Char, canvas: Canvas) {
    if (player == 'O') {
        canvas.drawCircle( x, y, 50, RED, 10)
    }
    if (player == 'X') {
        val translation = (canvas.width/3/3);
        canvas.drawLine(x-translation, y-translation, x+translation , y + translation, GREEN, 10)
        canvas.drawLine(x-translation, y+translation, x+translation , y - translation, GREEN, 10)

    }
}

fun draw(game: List<Char>, canvas: Canvas) {
    for (y in 0..2) {
        for (x in 0..2) {
            val i = x + y * 3

            val x = x * (canvas.width/3) + (canvas.width/3/2)
            val y = y * (canvas.height/3) + (canvas.height/3/2)

            drawIcon(x, y, game[i], canvas)
        }
    }
}

fun drawGame(game: List<Char>, player: Char, canvas: Canvas) {
    canvas.erase()
    draw(game, canvas)
    grid(canvas)
}

fun isBoardFull(game: List<Char>): Boolean {
    return !game.contains('_')
}

fun won(game: List<Char>, player: Char): Boolean {
    return listOf(
        listOf(0,1,2), listOf(3,4,5), listOf(6,7,8),
        listOf(0,3,6), listOf(1,4,7), listOf(2,5,8),
        listOf(0,4,8), listOf(2,4,6)
    ).any { (a,b,c) -> game[a]==player && game[b]==player && game[c]==player }

}

fun anyWon(game: List<Char>): Boolean {
    return won(game, 'O') or won(game, 'X')
}

fun validPosition(game: List<Char>, indice: Int): Boolean {
    if (game[indice] != '_') {
        println("Not a Valid Move, Select an Empty Cell")
        return false
    }

    return true
}

fun main() {
    var game: List<Char> = List(9) {
        '_'
    }
    var currentPlayer = 'X'
    onStart {
        val arena = Canvas(BOARD_SIZE, BOARD_SIZE, WHITE)
        grid(arena)

        arena.onMouseDown { me ->
            val i = getCell(me.x, me.y, arena)

            if (anyWon(game)) return@onMouseDown
            if (!validPosition(game, i)) return@onMouseDown

            game = registerGameMove(game, i, currentPlayer)
            drawGame(game, currentPlayer, arena)

            if (anyWon(game)) {
                arena.drawText(arena.width/4, arena.height/2, "$currentPlayer won the game!", BLACK)
            }
            else if (isBoardFull(game)) {
                arena.drawText(arena.width/2, arena.height/2, "It's a tie!", BLACK)
            }


            currentPlayer = if (currentPlayer == 'X') 'O' else 'X'
        }

        arena.onMouseMove { me ->
            if (!isBoardFull(game) and !anyWon(game)) {
                drawGame(game, currentPlayer, arena)
                drawIcon(me.x, me.y, currentPlayer, arena)
            }
        }
    }
    onFinish {
    }
}