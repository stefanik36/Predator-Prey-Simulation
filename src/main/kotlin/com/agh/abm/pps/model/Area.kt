package com.agh.abm.pps.model

import com.agh.abm.pps.model.species.Species
import com.agh.abm.pps.util.Benchmark

class Area(val species: MutableList<Species>) {
    var step: Int = 0


    fun nextStep() {

        println("Step $step")

        val alive = Benchmark.measure("Filter:"){species.filter { s -> s.alive }} as List<Species>
            Benchmark.measure("Move:") {alive.forEach { s -> s.move() }}
            Benchmark.measure("Consume:") {alive.parallelStream().forEach { s -> s.consume(this) }}
            Benchmark.measure("Others:") {alive.forEach { s -> s.performOtherActions(this) }}

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