package com.agh.abm.pps.model.board

import com.agh.abm.pps.model.species.Species
import com.agh.abm.pps.model.species.SpeciesType
import com.agh.abm.pps.util.Benchmark

class Area(val species: MutableList<Species>) {
    var reproducedSpecies: MutableList<Species> = mutableListOf()
    var step: Int = 0
    var numberOfSpecies: MutableMap<SpeciesType, Int> = mutableMapOf()
    private val chunkManager: ChunkManager = ChunkManager(900.0, 900.0, 20.0, 20.0)

    fun nextStep() {

        println("======================================")
        println("Step: $step, species: ${countAlive()}")

        val alive = species

        alive.forEach { a ->
            numberOfSpecies[a.getType()] = numberOfSpecies.getOrElse(a.getType(), { 0 }) + 1
        }

        Benchmark.measure("Move:") { alive.forEach { s -> s.move() } }
        Benchmark.measure("Fill chunk manager") { alive.forEach(chunkManager::addSpecies) }
        Benchmark.measure("Consume:") { alive.parallelStream().forEach { s -> s.consume(this) } }
        Benchmark.measure("Die:") { alive.forEach { s -> s.performDieActions() } }
        Benchmark.measure("Reproduce:") { alive.forEach { s -> s.reproduce(this) } }
        Benchmark.measure("Others:") { alive.forEach { s -> s.performOtherActions() } }
        Benchmark.measure("Remove dead agents") { species.removeIf { !it.energyTransferParameter.alive } }

        addReproducedSpecies()
        numberOfSpecies.clear()
        chunkManager.clear()

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
    }

    private fun countAlive(): Int {
        return species.filter { s -> s.energyTransferParameter.alive }.size
    }

    fun countSpecies(type: SpeciesType): Int {
        return numberOfSpecies.getOrElse(type, { 0 })
    }
}
