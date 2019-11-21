package com.agh.abm.pps.strategy.reproduce

import com.agh.abm.pps.model.board.Area
import com.agh.abm.pps.model.parameter.ReproduceParameter
import com.agh.abm.pps.model.species.Species
import kotlin.random.Random

enum class ReproduceStrategyType(name: String, val strategy: ReproduceStrategy) {
    PARAMETRIZED_PROBABILITY("Parametrized probability", ParametrizedProbabilityReproduceStrategy());

    override fun toString(): String {
        return name
    }
}

interface ReproduceStrategy {
    fun reproduce(species: Species,area: Area): Pair<List<Species>, Double>

    fun getType(): ReproduceStrategyType
}
