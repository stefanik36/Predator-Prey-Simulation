package com.agh.abm.pps

import com.agh.abm.pps.util.geometric.Vector
import com.agh.abm.pps.model.species.Predator
import com.agh.abm.pps.model.species.Prey
import com.agh.abm.pps.util.factory.SpeciesFactory
import tornadofx.*

fun main() {
    launch<GUI>()
}

class SimulationController : Controller() {
    private var isAlive = true

    val state: BoardState = BoardState(mutableListOf())

    /////////////////SETUP\\\\\\\\\\\\\\\\\\\
    private fun setupSimulation() {

    }
    /////////////////SETUP\\\\\\\\\\\\\\\\\\\

    /////////////////MAIN_LOOP\\\\\\\\\\\\\\\\\\\
    private fun loop() {
        println("OK")
        fire(
            NOTIFY_POPULATION_GRAPH(
                alivePredNum = state.guys.filterIsInstance<Predator>().count(),
                alivePreyNum = state.guys.filterIsInstance<Prey>().count()
            )
        )
        fire(UPDATE_BOARDVIEW)
        Thread.sleep(500)
    }
    /////////////////MAIN_LOOP\\\\\\\\\\\\\\\\\\\

    init {
        subscribe<EXIT> { isAlive = false }
        subscribe<START> {
            isAlive = true
            runAsync(daemon = false) { while (isAlive) loop() }
        }
        setupSimulation()
    }

    fun addGuy(x: Double, y: Double, type: GuyType) {
        when (type) {
            GuyType.PRAY ->
                state.guys.add(SpeciesFactory.standardPrey(Vector(x, y)))
            GuyType.PREDATOR ->
                state.guys.add(SpeciesFactory.standardPredator(Vector(x, y)))
        }
        fire(UPDATE_BOARDVIEW)
    }

    fun removeGuy(x: Double, y: Double) {
        state.guys.removeIf { c ->
            val cx = c.currentPosition.x
            val cy = c.currentPosition.y
            cx - c.size < x && cx + c.size > x && cy - c.size < y && cy + c.size > y
        }
        fire(UPDATE_BOARDVIEW)
    }

    fun clearBoard() {
        state.guys.clear()
        fire(UPDATE_BOARDVIEW)
    }

}
