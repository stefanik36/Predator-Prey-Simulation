package com.agh.abm.pps.model

import com.agh.abm.pps.model.species.Species

class Area(var species: List<Species>) {
    var step: Int = 0


    fun nextStep() {
        var alive = species.filter { s -> s.alive }
        alive.parallelStream().forEach { s -> s.move() }
        alive.parallelStream().forEach { s -> s.eat(this) }
        alive.parallelStream().forEach { s -> s.performOtherActions() }
        step++
    }


    fun getOverview(): String {
        return species.joinToString(
            ",${System.lineSeparator()}\t",
            "AREA [$step]:${System.lineSeparator()}\t"
        ) { s -> s.getOverview() }
    }
}