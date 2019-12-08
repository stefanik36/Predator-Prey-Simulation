package com.agh.abm.pps.model.board

import com.agh.abm.pps.gui.BoardState
import com.agh.abm.pps.model.parameter.SpeciesParameter
import com.agh.abm.pps.model.species.Species
import com.agh.abm.pps.util.Benchmark
import com.agh.abm.pps.util.default_species.DefaultSpecies
import com.agh.abm.pps.util.geometric.PositionRestriction


class Area(boardState: BoardState) {
    val speciesTypes: MutableMap<String, SpeciesParameter> = mutableMapOf(
        Pair("GRASS", DefaultSpecies.grassParameters),
        Pair("BUSH", DefaultSpecies.bushParameters),
        Pair("PREY", DefaultSpecies.preyParameters),
        Pair("PREDATOR", DefaultSpecies.predatorParameters)
    )

    val species = mutableListOf<Species>()
    var reproducedSpecies: MutableList<Species> = mutableListOf()
    var step: Int = 0
    var numberOfSpecies: MutableMap<String, Int> = mutableMapOf()
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
        Benchmark.measure("Move:") { species.forEach { s -> s.move(this@Area) } }
        Benchmark.measure("Fill chunk manager") { species.forEach { chunkManager.addSpecies(it) } }
        Benchmark.measure("Count species") {
            speciesTypes.forEach { st ->
                chunkManager.chunks.forEach { ch ->
                    numberOfSpecies[st.key] = (numberOfSpecies[st.key] ?: 0) + (ch.speciesNumber[st.key] ?: 0)
                }
            }
        }
        Benchmark.measure("Consume:") { species.parallelStream().forEach { s -> s.consume() } }
        Benchmark.measure("Die:") { species.forEach { s -> s.performDieActions() } }
        Benchmark.measure("Reproduce:") { species.forEach { s -> s.reproduce(this@Area) } }
        Benchmark.measure("Others:") { species.forEach { s -> s.performOtherActions() } }
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

    fun countSpecies(type: String): Int {
        return numberOfSpecies.getOrElse(type, { 0 })
    }
}
