package com.agh.abm.pps.model.board

import com.agh.abm.pps.model.species.Species
import com.agh.abm.pps.model.species.SpeciesType
import com.agh.abm.pps.util.Benchmark

class Area(val species: MutableList<Species>) {
    var step: Int = 0


    fun nextStep() {

        println("======================================")
        println("Step: $step, species: ${countAlive()}")

        val alive =
            Benchmark.measure("Filter:") { species.filter { s -> s.energyTransferParameter.alive } } as List<Species>
        Benchmark.measure("Move:") { alive.forEach { s -> s.move() } }
        Benchmark.measure("Consume:") { alive.parallelStream().forEach { s -> s.consume(this) } }
        Benchmark.measure("Others:") { alive.forEach { s -> s.performOtherActions(this) } }

        step++

        println(getOverview())
    }


    fun getOverview(): String {
        return species.filter { s -> s.getType() == SpeciesType.PREY }.joinToString(
            ",${System.lineSeparator()}\t",
            "AREA " +
                    "[step: $step] " +
                    "[alive: ${species.filter { s -> s.energyTransferParameter.alive }.size}]:${System.lineSeparator()}\t"
        ) { s -> s.getOverview() }
    }

    fun add(newSpecies: List<Species?>) {
        species.addAll(newSpecies.filterNotNull().toMutableList())
    }

    private fun countAlive(): Int {
        return species.filter { s -> s.energyTransferParameter.alive }.size
    }

    fun countSpecies(type: SpeciesType): Int {
        return species.filter { s -> s.energyTransferParameter.alive }.filter { s -> s.getType() == type }.size
    }
}
