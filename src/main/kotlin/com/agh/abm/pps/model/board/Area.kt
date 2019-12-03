package com.agh.abm.pps.model.board

import com.agh.abm.pps.gui.BoardState
import com.agh.abm.pps.model.species.Species
import com.agh.abm.pps.model.species.SpeciesType
import com.agh.abm.pps.util.Benchmark
import com.agh.abm.pps.util.geometric.PositionRestriction

class Area(boardState: BoardState) {
    val species = mutableListOf<Species>()
    var reproducedSpecies: MutableList<Species> = mutableListOf()
    var step: Int = 0
    var numberOfSpecies: MutableMap<SpeciesType, Int> = mutableMapOf()
    private val chunkManager: ChunkManager =
        ChunkManager(boardState.width, boardState.height, boardState.chunkSize, boardState.chunkSize)

    val restriction: PositionRestriction = PositionRestriction(
        minX = 0.0,
        maxX = boardState.width,
        minY = 0.0,
        maxY = boardState.height
    )


    fun nextStep() {

        println("======================================")
        println("Step: $step, species: ${countAlive()}")


        Benchmark.measure("Move:") { species.forEach { s -> s.move(this) } }
        Benchmark.measure("Fill chunk manager") {
            species.forEach {
                numberOfSpecies[it.getType()] = numberOfSpecies.getOrElse(it.getType(), { 0 }) + 1
                chunkManager.addSpecies(it)
            }
        }
        Benchmark.measure("Consume:") { species.parallelStream().forEach { s -> s.consume(this) } }
        Benchmark.measure("Die:") { species.forEach { s -> s.performDieActions() } }
        Benchmark.measure("Reproduce:") { species.forEach { s -> s.reproduce(this) } }
        Benchmark.measure("Others:") { species.forEach { s -> s.performOtherActions() } }
        Benchmark.measure("Remove dead agents") { this.species.removeIf { !it.energyTransferParameter.alive } }

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
