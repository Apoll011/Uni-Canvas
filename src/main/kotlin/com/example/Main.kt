package com.example

import pt.isel.canvas.*

fun grid(canvas: Canvas) {
    canvas.drawLine(canvas.width/3, 0, canvas.width/3, canvas.height)
    canvas.drawLine(canvas.width*2/3, 0, canvas.width*2/3, canvas.height)
    canvas.drawLine(0, canvas.height/3, canvas.width, canvas.height/3)
    canvas.drawLine(0, canvas.height*2/3, canvas.width, canvas.height*2/3)
}

fun get_cell(x: Int, y: Int, canvas: Canvas): Int {
    val w_s = canvas.width/3
    val h_s = canvas.height/3

    val pos_x = if (x < w_s) 0 else if (x < w_s*2) 1 else 2
    val pos_y = if (y < h_s) 0 else if (y < h_s*2) 1 else 2

    return pos_x + pos_y * 3
}

fun register_game_move(game: MutableList<Char>, indice: Int, player: Char) : MutableList<Char> {
    if (game[indice] != '_') {
        println("Not a Valid Move, Select an Empty Cell")
        return game
    }

    game[indice] = player
    return game
}

fun draw_icon(x: Int, y: Int, player: Char, canvas: Canvas) {
    if (player == 'O') {
        canvas.drawCircle( x, y, 50, RED, 10)
    }
    if (player == 'X') {
        val translation = (canvas.width/3/3);
        canvas.drawLine(x-translation, y-translation, x+translation , y + translation, GREEN, 10)
        canvas.drawLine(x-translation, y+translation, x+translation , y - translation, GREEN, 10)

    }
}

fun draw(game: MutableList<Char>, canvas: Canvas) {
    for (y in 0..2) {
        for (x in 0..2) {
            val i = x + y * 3

            val x = x * (canvas.width/3) + (canvas.width/3/2)
            val y = y * (canvas.height/3) + (canvas.height/3/2)

            draw_icon(x, y, game[i], canvas)
        }
    }
}

fun draw_game(game: MutableList<Char>, player: Char, canvas: Canvas) {
    canvas.erase()
    draw(game, canvas)
    grid(canvas)
}

fun is_board_full(game: MutableList<Char>): Boolean {
    return !game.contains('_')
}

fun won(game: MutableList<Char>, player: Char): Boolean {
    return listOf(
        listOf(0,1,2), listOf(3,4,5), listOf(6,7,8),
        listOf(0,3,6), listOf(1,4,7), listOf(2,5,8),
        listOf(0,4,8), listOf(2,4,6)
    ).any { (a,b,c) -> game[a]==player && game[b]==player && game[c]==player }

}

fun any_won(game: MutableList<Char>): Boolean {
    return won(game, 'O') or won(game, 'X')
}

fun main() {
    var game: MutableList<Char> = MutableList(9) {
        '_'
    }
    var currentPlayer = 'X'
    onStart {
        val arena = Canvas(500, 500, WHITE)
        grid(arena)

        arena.onMouseDown { me ->
            if (any_won(game)) return@onMouseDown

            val i = get_cell(me.x, me.y, arena)

            game = register_game_move(game, i, currentPlayer)
            draw_game(game, currentPlayer, arena)

            if (any_won(game)) {
                arena.drawText(arena.width/4, arena.height/2, "$currentPlayer won the game!", BLACK)
            }

            currentPlayer = if (currentPlayer == 'X') 'O' else 'X'
        }

        arena.onMouseMove { me ->
            if (!is_board_full(game) and !any_won(game)) {
                draw_game(game, currentPlayer, arena)
                draw_icon(me.x, me.y, currentPlayer, arena)
            }
        }
    }
    onFinish {
    }
}