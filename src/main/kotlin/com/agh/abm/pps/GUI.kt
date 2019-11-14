package com.agh.abm.pps

import com.agh.abm.pps.model.species.*
import com.agh.abm.pps.gui.ConfigView
import javafx.beans.value.ObservableValue
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.control.ComboBox
import javafx.scene.input.MouseButton
import javafx.scene.paint.Color
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import javafx.scene.control.Label
import javafx.scene.control.Slider
import tornadofx.*

data class BoardState(
    val width: Double,
    val height: Double,
    val guys: MutableList<Species>
)

object EXIT : FXEvent()
class START(val delay: Long) : FXEvent()
object UPDATE_BOARDVIEW : FXEvent()

class NOTIFY_POPULATION_GRAPH(val alivePredNum: Int, val alivePreyNum: Int, val aliveGrassNum: Int) : FXEvent()
class NOTIFY_DELAY_CHANGE(val delay: Long) : FXEvent()

class Board : View() {

    private var canv: Canvas by singleAssign()
    private var gc: GraphicsContext

    private var typeSelect: ComboBox<SpeciesType> by singleAssign()
    private var spawnNumSlider: Slider by singleAssign()
    private var spawnAreaSizeSlider: Slider by singleAssign()
    private var delaySlider: Slider by singleAssign()
    private var statusLabel: Label by singleAssign()

    private val controller: SimulationController by inject()
    private val configView: ConfigView by inject()

    override val root = group {
        vbox {
            spacing = 5.0
            hbox {
                alignment = Pos.CENTER_LEFT
                spacing = 5.0
                padding = Insets(0.0, 0.0, 0.0, 15.0)
                button("Clear") { action { controller.clearBoard() } }
                button("Start") {
                    action {
                        fire(START(delaySlider.value.toLong()))
                        statusLabel.text = "Status: Running"
                        statusLabel.textFill = Color.GREEN
                    }
                }
                button("Pause") {
                    action {
                        fire(EXIT)
                        statusLabel.text = "Status: Stopped"
                        statusLabel.textFill = Color.RED
                    }
                }

                typeSelect = combobox(values = listOf(SpeciesType.PREDATOR, SpeciesType.PREY, SpeciesType.GRASS)) {
                    selectionModel.select(0)
                }
                label("Spawn number:")
                spawnNumSlider = slider {
                    padding = Insets(0.0, 0.0, 0.0, 0.0)
                    min = 1.0
                    max = 500.0
                    value = 1.0
                    isShowTickLabels = true
                    isShowTickMarks = true
                    majorTickUnit = 500.0
                }
                label("Spawn area size:")
                spawnAreaSizeSlider = slider {
                    padding = Insets(0.0, 0.0, 0.0, 0.0)
                    min = 1.0
                    max = 500.0
                    value = 1.0
                    isShowTickLabels = true
                    isShowTickMarks = true
                    majorTickUnit = 500.0
                }
            }
            hbox {
                alignment = Pos.CENTER_LEFT
                spacing = 5.0
                padding = Insets(0.0, 0.0, 0.0, 15.0)
                button("Config") {
                    action { configView.openWindow() }
                }
                button("Show graph!") { action { PopulationGraphView().openWindow() } }
                label("Delay:")
                delaySlider = slider {
                    min = 1.0
                    max = 1000.0
                    value = 200.0
                    isShowTickLabels = true
                    isShowTickMarks = true
                    majorTickUnit = 1000.0

                }

                statusLabel = label("Status:"){
                    padding = Insets(2.0, 5.0, 2.0, 20.0)
                }
            }
            canv = canvas(controller.board.width, controller.board.height)
        }
    }

    init {
        subscribe<UPDATE_BOARDVIEW> { updateView(controller.board) }

        gc = canv.graphicsContext2D

        delaySlider.valueProperty()
            .addListener(ChangeListener() { _: ObservableValue<out Number>?, _: Number, number1: Number ->
                fire(NOTIFY_DELAY_CHANGE(number1.toLong()))
            })

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

