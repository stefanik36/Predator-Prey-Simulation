package com.agh.abm.pps.gui.layout

import javafx.beans.property.DoubleProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle

@Deprecated("To remove")
internal class PannableCanvas(width: Double, height: Double) : Pane() {

    val canvas = Canvas(width, height)
    val gc: GraphicsContext = canvas.graphicsContext2D

    var myScale: DoubleProperty = SimpleDoubleProperty(1.0)

    var scale: Double
        get() = myScale.get()
        set(scale) = myScale.set(scale)

    init {
//        setPrefSize(width, height)
//        style = "-fx-background-color: lightgrey; -fx-border-color: blue;"
        children.add(createBorder(width, height))
        children.add(canvas)

        // add scale transform
        scaleXProperty().bind(myScale)
        scaleYProperty().bind(myScale)
    }

    private fun createBorder(width: Double, height: Double): Rectangle {
        val border = Rectangle(width, height)
        border.stroke = Color.BLACK
        border.fill = Color.WHITE
        return border
    }

    fun setPivot(x: Double, y: Double) {
        translateX -= x
        translateY -= y
    }
}