package com.agh.abm.pps

import com.agh.abm.pps.model.board.Area
import com.agh.abm.pps.model.species.*
import com.agh.abm.pps.util.geometric.Vector
import com.agh.abm.pps.gui.SpeciesConfData
import com.agh.abm.pps.util.Benchmark
import tornadofx.*
import kotlin.random.Random

fun main() {
    launch<GUI>()
}

class SimulationController : Controller() {
    private var isAlive = true
    private var delay: Long = 1
    val board: BoardState = BoardState(800.0, 600.0, 20.0)
    lateinit var area: Area

    /////////////////SETUP\\\\\\\\\\\\\\\\\\\
    private fun setupSimulation() {
        area = Area(board)
    }
    /////////////////SETUP\\\\\\\\\\\\\\\\\\\

    /////////////////MAIN_LOOP\\\\\\\\\\\\\\\\\\\
    private fun loop() {
        val flexibleDelay = delay - Benchmark.measure {
            area.nextStep()
            fire(
                NOTIFY_POPULATION_GRAPH(
                    alivePredNum = board.agents.filterIsInstance<Predator>().count(),
                    alivePreyNum = board.agents.filterIsInstance<Prey>().count(),
                    aliveGrassNum = board.agents.filterIsInstance<Grass>().count()
                )
            )
            fire(UPDATE_BOARDVIEW)
        }
        Thread.sleep(if (flexibleDelay > 0) flexibleDelay else 1)
    }
    /////////////////MAIN_LOOP\\\\\\\\\\\\\\\\\\\

    init {
        subscribe<EXIT> { isAlive = false }
        subscribe<START> { t ->
            delay = t.delay
            isAlive = true
            runAsync(daemon = false) { while (isAlive) loop() }
        }
        subscribe<NOTIFY_DELAY_CHANGE> { t -> delay = t.delay }
        setupSimulation()
    }

    fun addGuy(x: Double, y: Double, conf: SpeciesConfData, number: Double, areaSize: Double) {
        initSpecies(x, y, areaSize, number) { ix, iy -> conf.createSpecies(Vector(ix, iy)) }
        fire(UPDATE_BOARDVIEW)
    }

    fun removeGuy(x: Double, y: Double) {
        board.agents.removeIf { c ->
            val cx = c.movementParameter.currentPosition.x
            val cy = c.movementParameter.currentPosition.y
            val size = c.guiParameter.size
            cx - size < x && cx + size > x && cy - size < y && cy + size > y
        }
        fire(UPDATE_BOARDVIEW)
    }

    private fun initSpecies(x: Double, y: Double, range: Double, numb: Double, fac: (Double, Double) -> Species) {
        val fromX = if (x - range > 0) x - range else 0.0
        val toX = if (x + range < board.width) x + range else board.width
        val fromY = if (y - range > 0) y - range else 0.0
        val toY = if (y + range < board.height) y + range else board.height

        for (i in 1..numb.toInt()) {
            val ix = Random.nextDouble(fromX, toX)
            val iy = Random.nextDouble(fromY, toY)
            board.agents.add(fac(ix, iy))
        }
    }

    fun clearBoard() {
        board.agents.clear()
        fire(UPDATE_BOARDVIEW)
    }

}
