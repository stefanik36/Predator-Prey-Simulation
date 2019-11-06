package com.agh.abm.pps

import com.agh.abm.pps.species.Predator
import com.agh.abm.pps.species.Prey
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
        fire(UPDATEBOARDVIEW)
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
                state.guys.add(Prey.standard(Vector(x, y)))
            GuyType.PREDATOR ->
                state.guys.add(Predator.standard(Vector(x, y)))
        }
        fire(UPDATEBOARDVIEW)
    }

    fun removeGuy(x: Double, y: Double) {
        state.guys.removeIf { c ->
            val cx = c.currentPosition.x
            val cy = c.currentPosition.y
            cx - c.size < x && cx + c.size > x && cy - c.size < y && cy + c.size > y
        }
        fire(UPDATEBOARDVIEW)
    }

    fun clearBoard() {
        state.guys.clear()
        fire(UPDATEBOARDVIEW)
    }

}
