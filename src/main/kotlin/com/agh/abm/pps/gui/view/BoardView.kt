package com.agh.abm.pps.gui.view

import com.agh.abm.pps.SimulationController
import com.agh.abm.pps.gui.EXIT
import com.agh.abm.pps.gui.NOTIFY_DELAY_CHANGE
import com.agh.abm.pps.gui.START
import com.agh.abm.pps.gui.UPDATE_BOARDVIEW
import com.agh.abm.pps.gui.layout.BoardCanvas
import com.agh.abm.pps.model.species.Species
import com.agh.abm.pps.util.Benchmark
import javafx.beans.value.ObservableValue
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.canvas.GraphicsContext
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.control.Slider
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.input.ScrollEvent
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import tornadofx.*

class BoardView : View() {

    //    private var pannableCanvas: PannableCanvas by singleAssign()
    private var canv: BoardCanvas by singleAssign()
    private val gc: GraphicsContext

    private var typeSelect: ComboBox<String> by singleAssign()
    private var spawnNumSlider: Slider by singleAssign()
    private var spawnAreaSizeSlider: Slider by singleAssign()
    private var delaySlider: Slider by singleAssign()
    private var statusLabel: Label by singleAssign()

    private val controller: SimulationController by inject()
    private val configView: ConfigView by inject()

    private var pane: Pane by singleAssign()
    override val root = group {
        //        pannableCanvas = opcr(
//            this,
//            PannableCanvas(controller.board.width, controller.board.height)
//        )
        pane = pane {
            //            background = Color.rgb(227, 227, 227).asBackground()

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

                    typeSelect = combobox(
                        values = listOf( //TODO get from area.speciesTypes.values
                            "GRASS",
                            "BUSH",
                            "PREY",
                            "PREDATOR"
                        )
                    ) {
                        selectionModel.select(0)
                    }
                    label("Spawn number:")
                    spawnNumSlider = slider {
                        padding = Insets(0.0, 0.0, 0.0, 0.0)
                        min = 1.0
                        max = 500.0
                        value = 500.0
                        isShowTickLabels = true
                        isShowTickMarks = true
                        majorTickUnit = 500.0
                    }
                    label("Spawn area size:")
                    spawnAreaSizeSlider = slider {
                        padding = Insets(0.0, 0.0, 0.0, 0.0)
                        min = 1.0
                        max = 500.0
                        value = 500.0
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

                    statusLabel = label("Status:") {
                        padding = Insets(2.0, 5.0, 2.0, 20.0)
                    }
                }

                canv = opcr(
                    this,
                    BoardCanvas(900.0, 900.0, controller.board)
                )
            }
        }
    }

    init {
        configView.force()
        subscribe<UPDATE_BOARDVIEW> { canv.requestUpdate() }
        gc = canv.graphicsContext2D

        root.addEventFilter(MouseEvent.MOUSE_PRESSED, canv.gestures.onMousePressedEventHandler)
        root.addEventFilter(MouseEvent.MOUSE_DRAGGED, canv.gestures.onMouseDraggedEventHandler)
        root.addEventFilter(ScrollEvent.ANY, canv.gestures.onScrollEventHandler)


        canv.updateBoardFunc = {
            Benchmark.measure("Draw board ") {
                val laterDraw = mutableListOf<Species>()
                controller.board.agents.forEach { guy ->
                    when (guy.speciesName) {
                        "GRASS" -> draw(guy)
                        else -> laterDraw.add(guy)
                    }
                }
                laterDraw.forEach { draw(it) }
            }
        }


        primaryStage.widthProperty().addListener { obs, oldVal, newVal -> pane.prefWidth = newVal.toDouble() }
        delaySlider.valueProperty()
            .addListener(ChangeListener() { _: ObservableValue<out Number>?, _: Number, number1: Number ->
                fire(NOTIFY_DELAY_CHANGE(number1.toLong()))
            })



        canv.onMouseClicked = EventHandler { e ->
            when (e.button) {
                MouseButton.PRIMARY -> {
                    val realXY = canv.getRealXY(e)
                    controller.addGuy(
                        realXY.first,
                        realXY.second,
                        configView.species.first { it.type == typeSelect.selectionModel.selectedItem },
                        spawnNumSlider.value,
                        spawnAreaSizeSlider.value
                    )
//                    println("${e.x} || ${e.y}")
                }
//                MouseButton.SECONDARY -> controller.removeGuy(e.x, e.y)
                MouseButton.MIDDLE -> when (typeSelect.selectionModel.selectedIndex) {
//                    TODO fix for more com.agh.abm.pps.model.species types
                    2 -> typeSelect.selectionModel.selectFirst()
                    else -> typeSelect.selectionModel.selectNext()
                }
                else -> {
                }
            }
        }
    }


    private fun drawCircle(x: Double, y: Double, size: Double, color: Color) {
        gc.fill = color
        gc.fillOval(x - size / 2, y - size / 2, size, size)

    }

    private fun drawViewRange(x: Double, y: Double, size: Double) {
        gc.stroke = Color.GRAY
        gc.lineWidth = 1.0
        gc.strokeOval(x - size / 2, y - size / 2, size, size)
    }


    private fun draw(o: Species) {
        drawCircle(
            o.movementParameter.currentPosition.x,
            o.movementParameter.currentPosition.y,
            o.guiParameter.size,
            o.guiParameter.color
        )
        drawViewRange(
            o.movementParameter.currentPosition.x,
            o.movementParameter.currentPosition.y,
            o.consumeParameter.consumeRange
        )
    }

    override fun onUndock() {
        fire(EXIT)
        super.onUndock()
    }
}
