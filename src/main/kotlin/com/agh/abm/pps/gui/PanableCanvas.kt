package com.agh.abm.pps.gui

import javafx.beans.property.DoubleProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle

internal class PannableCanvas(width: Double, height: Double) : Pane() {

    val canvas = Canvas(width, height)
    val gc: GraphicsContext = canvas.graphicsContext2D

    var myScale: DoubleProperty = SimpleDoubleProperty(1.0)

    var scale: Double
        get() = myScale.get()
        set(scale) = myScale.set(scale)


    init {
        setPrefSize(width, height)
//        style = "-fx-background-color: lightgrey; -fx-border-color: blue;"
        val borders: Rectangle = Rectangle(width, height)
        borders.stroke = Color.BLUE
        borders.fill = Color.WHITE
        children.add(borders)
        children.add(canvas)
        // add scale transform
        scaleXProperty().bind(myScale)
        scaleYProperty().bind(myScale)
    }

    /**
     * Add a grid to the canvas, send it to back
     */
//    fun addGrid() {
//
//        val w = boundsInLocal.width
//        val h = boundsInLocal.height
//
//        // add grid
//        val grid = Canvas(w, h)
//
//        // don't catch mouse events
//        grid.isMouseTransparent = true
//
//        val gc = grid.graphicsContext2D
//
//        gc.stroke = Color.GRAY
//        gc.lineWidth = 1.0
//
//        // draw grid lines
//        val offset = 50.0
//        var i = offset
//        while (i < w) {
//            gc.strokeLine(i, 0.0, i, h)
//            gc.strokeLine(0.0, i, w, i)
//            i += offset
//        }
//
//        children.add(grid)
//
//        grid.toBack()
//    }

    fun setPivot(x: Double, y: Double) {
        translateX = translateX - x
        translateY = translateY - y
    }
}