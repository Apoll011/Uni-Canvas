package com.example

import pt.isel.canvas.Canvas

class Arena(val gridsX: Int, val gridsY: Int, val size: Int)

/**
 * Draws a grid on the canvas.
 * @receiver The canvas on which to draw the grid.
 */
fun Arena.draw(canvas: Canvas) {
    for (i in 0..gridsX) {
        canvas.drawLine(size*i, 0, size*i, canvas.height)
    }
    for (i in 0..gridsY) {
        canvas.drawLine(0, size*i, canvas.width, size*i)
    }
}