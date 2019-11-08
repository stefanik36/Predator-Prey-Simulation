package com.agh.abm.pps.model

import com.agh.abm.pps.model.species.Species

class Area(val species: MutableList<Species>) {
    var step: Int = 0


    fun nextStep() {

        val alive = species.filter { s -> s.alive }
        alive.parallelStream().forEach { s -> s.move() }
        alive.parallelStream().forEach { s -> s.consume(this) }
        alive.parallelStream().forEach { s -> s.performOtherActions(this) }
        step++


    }


    fun getOverview(): String {
        return species.joinToString(
            ",${System.lineSeparator()}\t",
            "AREA [$step] " +
                    "alive [${species.filter { s -> s.alive }}]:${System.lineSeparator()}\t"
        ) { s -> s.getOverview() }
    }

    fun add(newSpecies: List<Species?>) {
        species.addAll(newSpecies.filterNotNull().toMutableList())

    }
}