package com.agh.abm.pps

import com.agh.abm.pps.model.Area
import com.agh.abm.pps.model.species.*
import com.agh.abm.pps.util.geometric.Vector
import com.agh.abm.pps.gui.SpeciesConfData
import tornadofx.*
import kotlin.random.Random

fun main() {
    launch<GUI>()
}

class SimulationController : Controller() {
    private var isAlive = true
    private var delay: Long = 1
    val board: BoardState = BoardState(900.0, 900.0, mutableListOf())
    lateinit var area: Area

    /////////////////SETUP\\\\\\\\\\\\\\\\\\\
    private fun setupSimulation() {
        area = Area(board.guys)
    }
    /////////////////SETUP\\\\\\\\\\\\\\\\\\\

    /////////////////MAIN_LOOP\\\\\\\\\\\\\\\\\\\
    private fun loop() {

        area.nextStep()
//        area.getOverview().also { println(it) }
        board.guys.removeIf { !it.alive }
        println(board.guys.size)
        fire(
            NOTIFY_POPULATION_GRAPH(
                alivePredNum = board.guys.filterIsInstance<Predator>().count(),
                alivePreyNum = board.guys.filterIsInstance<Prey>().count(),
                aliveGrassNum = board.guys.filterIsInstance<Grass>().count()
            )
        )
        fire(UPDATE_BOARDVIEW)
        Thread.sleep(delay)
    }
    /////////////////MAIN_LOOP\\\\\\\\\\\\\\\\\\\

    init {
        subscribe<EXIT> { isAlive = false }
        subscribe<START> {t ->
            delay = t.delay
            isAlive = true
            runAsync(daemon = false) { while (isAlive) loop() }
        }
        subscribe<NOTIFY_DELAY_CHANGE> { t -> delay = t.delay  }
        setupSimulation()
    }

    fun addGuy(x: Double, y: Double, conf: SpeciesConfData, number: Double, areaSize: Double) {
        initSpecies(x, y, areaSize, number) { x, y -> conf.createSpecies(Vector(x, y)) }
        fire(UPDATE_BOARDVIEW)
    }

    fun removeGuy(x: Double, y: Double) {
        board.guys.removeIf { c ->
            val cx = c.currentPosition.x
            val cy = c.currentPosition.y
            cx - c.size < x && cx + c.size > x && cy - c.size < y && cy + c.size > y
        }
        fire(UPDATE_BOARDVIEW)
    }

    private fun initSpecies(x: Double, y: Double, range: Double, numb: Double, fac: (Double, Double) -> Species) {
        val fromX = if (x - range > 0) x - range else 0.0
        val toX = if (x + range < board.width ) x + range else board.width
        val fromY = if (y - range > 0) y - range else 0.0
        val toY = if (y + range < board.height) y + range else board.height

        for (i in 1..numb.toInt()) {
            val x = Random.nextDouble(fromX, toX)
            val y = Random.nextDouble(fromY, toY)
            board.guys.add(fac(x, y))
        }
    }

    fun clearBoard() {
        board.guys.clear()
        fire(UPDATE_BOARDVIEW)
    }

}
