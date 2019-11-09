package com.agh.abm.pps

import com.agh.abm.pps.model.species.*
import com.agh.abm.pps.util.gui.ConfigView
import javafx.event.EventHandler
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.control.ComboBox
import javafx.scene.input.MouseButton
import javafx.scene.paint.Color
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import javafx.scene.control.Slider
import tornadofx.*

data class BoardState(
    val width: Double,
    val height: Double,
    val guys: MutableList<Species>
)

object EXIT : FXEvent()
object START : FXEvent()
object UPDATE_BOARDVIEW : FXEvent()

class NOTIFY_POPULATION_GRAPH(val alivePredNum: Int, val alivePreyNum: Int, val aliveGrassNum: Int) : FXEvent()

class Board : View() {

    private var canv: Canvas by singleAssign()
    private var gc: GraphicsContext

    private var typeSelect: ComboBox<SpeciesType> by singleAssign()
    private var spawnNumSlider: Slider by singleAssign()
    private var spawnAreaSizeSlider: Slider by singleAssign()

    val controller: SimulationController by inject()
    val configView: ConfigView by inject()

    override val root = group {
        canv = canvas(controller.board.width, controller.board.height)
        hbox {
            button("Clear") { action { controller.clearBoard() } }
            button("Start") { action { fire(START) } }
            button("Pause") { action { fire(EXIT) } }
            typeSelect = combobox(values = listOf(SpeciesType.PREDATOR, SpeciesType.PREY, SpeciesType.GRASS)) {
                selectionModel.select(0)
            }
            button("Show graph!") { action { PopulationGraphView().openWindow() } }
            label(" spawn number: ")
            spawnNumSlider = slider {
                min = 1.0
                max = 500.0
                value = 1.0
            }
            label(" spawn area size: ")
            spawnAreaSizeSlider = slider {
                min = 1.0
                max = 500.0
                value = 1.0
            }
            button("Config") {
                action { configView.openWindow() }
            }
        }

    }

    init {
        subscribe<UPDATE_BOARDVIEW> { updateView(controller.board) }

        gc = canv.graphicsContext2D

        canv.onMouseClicked = EventHandler { e ->
            when (e.button) {
                MouseButton.PRIMARY -> controller.addGuy(
                    e.x,
                    e.y,
                    configView.species.first { it.type == typeSelect.selectionModel.selectedItem },
                    spawnNumSlider.value,
                    spawnAreaSizeSlider.value
                )
                MouseButton.SECONDARY -> controller.removeGuy(e.x, e.y)
                MouseButton.MIDDLE -> when (typeSelect.selectionModel.selectedIndex) {
//                    TODO fix for more com.agh.abm.pps.model.species types
                    2 -> typeSelect.selectionModel.selectFirst()
                    else -> typeSelect.selectionModel.selectNext()
                }
                else -> {
                }
            }
        }

        fire(UPDATE_BOARDVIEW)
    }

    private fun drawCircle(x: Double, y: Double, size: Double, color: Color) {
        gc.fill = color
        gc.fillOval(x - size / 2, y - size / 2, size, size)

    }

    private fun drawViewRange(x: Double, y: Double, size: Double) {
        gc.stroke = Color.GRAY
        gc.lineWidth = 0.5
        gc.strokeOval(x - size / 2, y - size / 2, size, size)
    }


    private fun draw(o: Predator) {
        drawCircle(o.currentPosition.x, o.currentPosition.y, o.size, Color.RED)
        drawViewRange(o.currentPosition.x, o.currentPosition.y, o.consumeRange)
    }

    private fun draw(o: Prey) {
        drawCircle(o.currentPosition.x, o.currentPosition.y, o.size, Color.GREY)
        drawViewRange(o.currentPosition.x, o.currentPosition.y, o.consumeRange)
    }

    private fun draw(o: Grass) {
        gc.fill = Color.GREEN
        gc.fillRect(o.currentPosition.x, o.currentPosition.y, o.size, o.size)
    }

    private fun backgroundColor() {
        gc.fill = Color.DARKGREEN
        gc.fillRect(0.0, 0.0, controller.board.width, controller.board.height)
    }

    private fun clearBoard() {
        gc.clearRect(0.0, 0.0, controller.board.width, controller.board.height)
    }

    private fun updateView(state: BoardState) {
        clearBoard()
        state.guys.forEach { guy ->
            when (guy) {
                is Prey -> draw(guy)
                is Grass -> draw(guy)
                is Predator -> draw(guy)
            }

        }

    }

    override fun onUndock() {
        fire(EXIT)
        super.onUndock()
    }
}


class PopulationGraphView : View() {

    var preySeries: XYChart.Series<String, Number> by singleAssign()
    var predatorSeries: XYChart.Series<String, Number> by singleAssign()
    var grassSeries: XYChart.Series<String, Number> by singleAssign()
    var posi = 0

    override val root = group {
        linechart("Population graph", CategoryAxis(), NumberAxis()) {
            predatorSeries = series("Alive predators")
            preySeries = series("Alive prays")
            grassSeries = series("Alive grass")
        }
        button("Clear") {
            action {
                preySeries.data.clear()
                predatorSeries.data.clear()
                grassSeries.data.clear()
            }
        }
    }

    init {
        subscribe<NOTIFY_POPULATION_GRAPH> {
            preySeries.data.add(XYChart.Data(posi.toString(), it.alivePreyNum))
            predatorSeries.data.add(XYChart.Data(posi.toString(), it.alivePredNum))
            grassSeries.data.add(XYChart.Data(posi.toString(), it.aliveGrassNum))
            posi++
        }
    }

}


class GUI : App(Board::class, Styles::class)

class Styles : Stylesheet() {
    init {
//        Companion.label {
//            fontSize = 14.px
//            fontWeight = FontWeight.BOLD
//            backgroundColor += c("#cecece")
//        }
    }
}

