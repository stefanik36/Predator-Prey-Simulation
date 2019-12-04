package com.agh.abm.pps.gui

import com.agh.abm.pps.gui.style.Styles
import com.agh.abm.pps.gui.view.BoardView
import com.agh.abm.pps.gui.view.LaunchSettingsView
import com.agh.abm.pps.model.species.*
import tornadofx.*

data class BoardState(
    val width: Double,
    val height: Double,
    val chunkSize: Double,
    var agents: List<Species> = listOf()
)

object EXIT : FXEvent()
class START(val delay: Long) : FXEvent()
object UPDATE_BOARDVIEW : FXEvent()
class NOTIFY_POPULATION_GRAPH(val alivePredNum: Int, val alivePreyNum: Int, val aliveGrassNum: Int) : FXEvent()
class NOTIFY_DELAY_CHANGE(val delay: Long) : FXEvent()

class GUI : App(BoardView::class, Styles::class)

