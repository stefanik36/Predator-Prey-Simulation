package com.agh.abm.pps.strategy.reproduce

import com.agh.abm.pps.model.species.Species
import kotlin.random.Random

enum class ReproduceStrategyType(name: String, val strategy: ReproduceStrategy) {
    PARAMETRIZED_PROBABILITY("Parametrized probability", ParametrizedProbabilityReproduceStrategy(3.0)),
    PROBABILITY("Probability reproduce", ProbabilityReproduceStrategy());

    override fun toString(): String {
        return name
    }
}

interface ReproduceStrategy {
    fun reproduce(species: Species): Pair<List<Species>, Double>
    fun getType(): ReproduceStrategyType
}