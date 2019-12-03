package com.agh.abm.pps.gui.view

import com.agh.abm.pps.SimulationController
import com.agh.abm.pps.gui.*
import com.agh.abm.pps.gui.gesture.DragContext
import com.agh.abm.pps.gui.layout.PannableCanvas
import com.agh.abm.pps.gui.gesture.SceneGestures
import com.agh.abm.pps.model.species.*
import com.agh.abm.pps.util.Benchmark
import javafx.animation.AnimationTimer
import javafx.beans.value.ObservableValue
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.canvas.Canvas
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
    private var canv: Canvas by singleAssign()
    private val gc: GraphicsContext

    private var typeSelect: ComboBox<SpeciesType> by singleAssign()
    private var spawnNumSlider: Slider by singleAssign()
    private var spawnAreaSizeSlider: Slider by singleAssign()
    private var delaySlider: Slider by singleAssign()
    private var statusLabel: Label by singleAssign()

    private val controller: SimulationController by inject()
    private val configView: ConfigView by inject()

    private var pane: Pane by singleAssign()
    private var zoom = 1.0
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
                        values = listOf(
                            SpeciesType.PREDATOR,
                            SpeciesType.PREY,
                            SpeciesType.GRASS
                        )
                    ) {
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

                    statusLabel = label("Status:") {
                        padding = Insets(2.0, 5.0, 2.0, 20.0)
                    }
                }

                canv = canvas(900.0, 900.0)
            }
//            canv = canvas(controller.board.width, controller.board.height)
        }
    }

    private val sceneDragContext = DragContext()
    private var tX: Double = 0.0
    private var tY: Double = 0.0
    private var needToUpdateScreen = 0

    init {
        subscribe<UPDATE_BOARDVIEW> { needToUpdateScreen++ }
//        canv = pannableCanvas.canvas
        gc = canv.graphicsContext2D

//        val sceneGestures = SceneGestures(pannableCanvas)
        root.addEventFilter(MouseEvent.MOUSE_PRESSED, EventHandler {
            if (!it.isSecondaryButtonDown)
                return@EventHandler

            sceneDragContext.mouseAnchorX = it.sceneX
            sceneDragContext.mouseAnchorY = it.sceneY

            sceneDragContext.translateAnchorX = tX
            sceneDragContext.translateAnchorY = tY
        })
        root.addEventFilter(MouseEvent.MOUSE_DRAGGED, EventHandler {
            if (!it.isSecondaryButtonDown)
                return@EventHandler

            tX = sceneDragContext.translateAnchorX + it.sceneX - sceneDragContext.mouseAnchorX
            tY = sceneDragContext.translateAnchorY + it.sceneY - sceneDragContext.mouseAnchorY
//            updateView(controller.board)
            needToUpdateScreen++
            it.consume()
        })
        root.addEventFilter(ScrollEvent.ANY) {
            val delta = 1.2


            if (it.deltaY < 0)
                zoom /= delta
            else
                zoom *= delta

            needToUpdateScreen++
            it.consume()
        }
        primaryStage.widthProperty().addListener { obs, oldVal, newVal -> pane.prefWidth = newVal.toDouble() }
        delaySlider.valueProperty()
            .addListener(ChangeListener() { _: ObservableValue<out Number>?, _: Number, number1: Number ->
                fire(NOTIFY_DELAY_CHANGE(number1.toLong()))
            })



        canv.onMouseClicked = EventHandler { e ->
            when (e.button) {
                MouseButton.PRIMARY -> {
                    controller.addGuy(
                        (e.x - tX) / zoom,
                        (e.y - tY) / zoom,
                        configView.species.first { it.type == typeSelect.selectionModel.selectedItem },
                        spawnNumSlider.value,
                        spawnAreaSizeSlider.value
                    )
                    println("${e.x} || ${e.y}")
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

//        fire(UPDATE_BOARDVIEW)
        val a = object : AnimationTimer() {
            var x = 0
            override fun handle(p0: Long) {
                if (x > 2 && needToUpdateScreen > 0) {
                    updateView(controller.board)
                    needToUpdateScreen = 0
                    x = 0
                } else {
                    x++
                }
            }
        }
        a.start()
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


    private fun draw(o: Predator) {
        drawCircle(
            o.movementParameter.currentPosition.x,
            o.movementParameter.currentPosition.y,
            o.guiParameter.size,
            Color.RED
        )//TODO add color to guiParameter
        drawViewRange(
            o.movementParameter.currentPosition.x,
            o.movementParameter.currentPosition.y,
            o.consumeParameter.consumeRange
        )
    }

    private fun draw(o: Prey) {
        drawCircle(
            o.movementParameter.currentPosition.x,
            o.movementParameter.currentPosition.y,
            o.guiParameter.size,
            Color.rgb(245, 178, 7)
//            Color.BLACK
        )
        drawViewRange(
            o.movementParameter.currentPosition.x,
            o.movementParameter.currentPosition.y,
            o.consumeParameter.consumeRange
        )
    }

    private fun draw(o: Grass) {
        drawCircle(
            o.movementParameter.currentPosition.x,
            o.movementParameter.currentPosition.y,
            o.guiParameter.size,
            Color.rgb(96, 128, 56).deriveColor(1.0, 1.0, 1.0, .5)
//            Color.BLACK
        )
//        gc.fill = Color.rgb(96, 128, 56)
//        gc.fillRect(
//            o.movementParameter.currentPosition.x,
//            o.movementParameter.currentPosition.y,
//            o.guiParameter.size,
//            o.guiParameter.size
//        )
    }

    private fun clearBoard() {
        gc.clearRect(-1000.0, -1000.0, controller.board.width + 2000, controller.board.height + 2000)
    }

    private fun updateView(state: BoardState) {

        Benchmark.measure("Draw board ") {

            clearBoard()

            println("zoom: $zoom | tX: $tX | tY $tY")
            gc.setTransform(zoom, 0.0, 0.0, zoom, tX, tY)
            gc.strokeRect(0.0, 0.0, controller.board.width, controller.board.height)

            val laterDraw = mutableListOf<Species>()
            state.agents.forEach { guy ->
                when (guy) {
                    is Grass -> draw(guy)
                    else -> laterDraw.add(guy)
                }

            }
            laterDraw.forEach {
                when (it) {
                    is Prey -> draw(it)
                    is Predator -> draw(it)
                }
            }
        }
    }

    override fun onUndock() {
        fire(EXIT)
        super.onUndock()
    }
}