package com.agh.abm.pps.model.board

import com.agh.abm.pps.model.species.Species
import com.agh.abm.pps.model.species.SpeciesType
import com.agh.abm.pps.util.Benchmark

class Area(val species: MutableList<Species>) {
    var reproducedSpecies: MutableList<Species> = mutableListOf()
    var step: Int = 0


    fun nextStep() {

        println("======================================")
        println("Step: $step, species: ${countAlive()}")

        val alive =
            Benchmark.measure("Filter:") { species.filter { s -> s.energyTransferParameter.alive } } as List<Species>
        Benchmark.measure("Move:") { alive.forEach { s -> s.move() } }
        Benchmark.measure("Consume:") { alive.parallelStream().forEach { s -> s.consume(this) } }
        Benchmark.measure("Die:") { alive.parallelStream().forEach { s -> s.performDieActions() } }
        Benchmark.measure("Reproduce:") { alive.parallelStream().forEach { s -> s.reproduce(this) } }
        Benchmark.measure("Others:") { alive.parallelStream().forEach { s -> s.performOtherActions() } }

        addReproducedSpecies()

        step++

//        println(getOverview())
    }

    private fun addReproducedSpecies() {
        species.addAll(reproducedSpecies)
        reproducedSpecies.clear()
    }


    fun getOverview(): String {
//        return species.filter { s -> s.getType() == SpeciesType.PREY }.joinToString(
        return species.joinToString(
            ",${System.lineSeparator()}\t",
            "AREA " +
                    "[step: $step] " +
                    "[alive: ${species.filter { s -> s.energyTransferParameter.alive }.size}]:${System.lineSeparator()}\t"
        ) { s -> s.getOverview() }
    }

    fun add(newSpecies: List<Species>) {
        reproducedSpecies.addAll(newSpecies)
//        species.addAll(newSpecies.filterNotNull().toMutableList())
    }

    private fun countAlive(): Int {
        return species.filter { s -> s.energyTransferParameter.alive }.size
    }

    fun countSpecies(type: SpeciesType): Int {
        return species.filter { s -> s.energyTransferParameter.alive }.filter { s -> s.getType() == type }.size
    }
}
