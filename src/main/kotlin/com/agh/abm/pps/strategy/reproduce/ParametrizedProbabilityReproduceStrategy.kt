package com.agh.abm.pps.strategy.reproduce

import com.agh.abm.pps.model.species.Species
import com.agh.abm.pps.util.factory.VectorFactory
import kotlin.random.Random

class ParametrizedProbabilityReproduceStrategy(val multipleEnergy: Double) : ReproduceStrategy {
    private var random: Random = Random

    constructor(random: Random, multipleEnergy: Double) : this(multipleEnergy) {
        this.random = random;
    }

    override fun reproduce(species: Species): Pair<List<Species>, Double> {
        var speciesList = listOf<Species>()
        var cost = 0.0
        with(species) {
            if (random.nextDouble() <= reproduceProbability) {
                val numberOfOffspring =
                    if (species.maxNumberOfOffspring == 1) 1 else random.nextInt(1, species.maxNumberOfOffspring)
                speciesList = IntArray(numberOfOffspring) { it }
                    .map {
                        generate(VectorFactory.random(random, reproduceRange), reproduceCost * multipleEnergy)
                    }
                cost = speciesList.map { reproduceCost }.sum()
            }
        }
        return Pair(speciesList, cost)
    }
}