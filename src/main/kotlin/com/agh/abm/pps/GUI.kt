package com.agh.abm.pps

import com.agh.abm.pps.species.Predator
import com.agh.abm.pps.species.Prey
import javafx.event.EventHandler
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.control.ComboBox
import javafx.scene.input.MouseButton
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import com.agh.abm.pps.species.Species
import tornadofx.*

enum class GuyType {
    PREDATOR(), PRAY()
}

data class BoardState(val guys: MutableList<Species>)

object EXIT : FXEvent()
object START : FXEvent()
object UPDATEBOARDVIEW : FXEvent()

class Board : View() {
    private val width = 900.0
    private val height = 900.0

    private var canv: Canvas by singleAssign()
    private var gc: GraphicsContext

    private var typeSelect: ComboBox<GuyType> by singleAssign()

    val controller: SimulationController by inject()

    override val root = group {
        canv = canvas(width, height)
        hbox {
            button("Clear") { action { controller.clearBoard() } }
            button("Start") { action { fire(START) } }
            button("Pause") { action { fire(EXIT) } }
            typeSelect = combobox(values = listOf(GuyType.PRAY, GuyType.PREDATOR)) {
                selectionModel.select(0)
            }
        }

    }

    init {
        subscribe<UPDATEBOARDVIEW> { updateView(controller.state) }

        gc = canv.graphicsContext2D



        canv.onMouseClicked = EventHandler { e ->
            when (e.button) {
                MouseButton.PRIMARY -> controller.addGuy(e.x, e.y, typeSelect.selectionModel.selectedItem)
                MouseButton.SECONDARY -> controller.removeGuy(e.x, e.y)
                MouseButton.MIDDLE -> when (typeSelect.selectionModel.selectedIndex) {
                    0 -> typeSelect.selectionModel.selectNext()
                    1 -> typeSelect.selectionModel.selectPrevious()
                }
            }
        }

        fire(UPDATEBOARDVIEW)
    }

    private fun drawCircle(x: Double, y: Double, size: Double, color: Color) {
        gc.fill = color
        gc.fillOval(x - size/2, y - size/2, size, size)
    }

    private fun drawViewRange(x: Double, y: Double, size: Double){
        gc.stroke = Color.GRAY
        gc.lineWidth = 0.5
        gc.strokeOval(x- size/2, y - size/2, size, size)
    }

    private fun clearBoard() {
        gc.clearRect(0.0, 0.0, width, height)
    }

    private fun updateView(state: BoardState) {
        clearBoard()
        state.guys.forEach { guy ->
            val color = when (guy) {
                is Predator -> Color.RED
                is Prey -> Color.GREEN
                else -> Color.BLACK
            }

            drawCircle(guy.currentPosition.x, guy.currentPosition.y, guy.size, color)
            drawViewRange(guy.currentPosition.x, guy.currentPosition.y, guy.range)
        }

    }

    override fun onUndock() {
        fire(EXIT)
        super.onUndock()
    }
}

class GUI : App(Board::class, Styles::class)

class Styles : Stylesheet() {
    init {
        Companion.label {
            fontSize = 20.px
            fontWeight = FontWeight.BOLD
            backgroundColor += c("#cecece")
        }
    }
}

