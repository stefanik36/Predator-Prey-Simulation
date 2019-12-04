package com.agh.abm.pps.gui.layout

import com.agh.abm.pps.gui.BoardState
import com.agh.abm.pps.gui.gesture.BoardGestures
import javafx.animation.AnimationTimer
import javafx.scene.canvas.Canvas
import javafx.scene.input.MouseEvent

class BoardCanvas(width: Double, height: Double, val board: BoardState) : Canvas(width, height) {

    val gestures = BoardGestures(this)
    private var needToUpdate = 1
    var updateBoardFunc: () -> Unit = {}

    var zoom = 1.0
    var tX = 0.0
    var tY = 0.0

    private val timer = object : AnimationTimer() {
        override fun handle(p0: Long) {
            if (needToUpdate > 0) {
                hardClear()
                initBoard()
                updateBoardFunc()
                needToUpdate = 0
            }
        }
    }

    init {
        timer.start()
    }

    fun requestUpdate() {
        needToUpdate++
    }

    private fun clear() {
        graphicsContext2D.clearRect(-50.0, -50.0, width + 100.0, height + 100.0)
    }

    private fun hardClear() {
        graphicsContext2D.clearRect(-1000.0, -1000.0, board.width + 2000, board.height + 2000)
    }

    private fun initBoard() {
        graphicsContext2D.setTransform(
            zoom,
            0.0,
            0.0,
            zoom,
            tX,
            tY
        )
        graphicsContext2D.strokeRect(0.0, 0.0, board.width, board.height)
    }

    fun getRealXY(e: MouseEvent): Pair<Double, Double>{
        val x = (e.x - tX) / zoom
        val y = (e.y - tY) / zoom
        return Pair(x,y)
    }
}