import pt.isel.canvas.*


const val CELL_SIZE = 128
const val GRID_WIDTH = 5
const val GRID_HEIGHT = 3

data class Cell(val col:Int, val row:Int)
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

enum class Dir { LEFT, RIGHT, UP, DOWN }

fun main() {
    onStart {
        val canvas = Canvas(GRID_WIDTH*CELL_SIZE, GRID_HEIGHT*CELL_SIZE, WHITE)
        val Arena = Arena(GRID_WIDTH, GRID_HEIGHT, CELL_SIZE)
        Arena.draw(canvas)
    }
    onFinish {  }
}