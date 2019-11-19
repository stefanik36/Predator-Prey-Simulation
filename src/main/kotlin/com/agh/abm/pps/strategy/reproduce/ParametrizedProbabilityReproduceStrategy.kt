package com.agh.abm.pps.strategy.reproduce

import com.agh.abm.pps.model.species.Species
import com.agh.abm.pps.util.factory.VectorFactory
import kotlin.random.Random

class ParametrizedProbabilityReproduceStrategy() : ReproduceStrategy {

    private var random: Random = Random

    constructor(random: Random) : this() {
        this.random = random;
    }

    override fun reproduce(species: Species, multiplyEnergy: Double, addEnergy: Double): Pair<List<Species>, Double> {
        var speciesList = listOf<Species>()
        var cost = 0.0
        if (random.nextDouble() <= species.reproduceProbability) {

            val numberOfOffspring =
                if (species.maxNumberOfOffspring == 1) 1
                else random.nextInt(1, species.maxNumberOfOffspring)

            speciesList = IntArray(numberOfOffspring) { it }
                .map {
                    species.generate(
                        VectorFactory.random(random, species.reproduceRange).add(species.currentPosition),
                        species.reproduceCost * multiplyEnergy + addEnergy
                    )
                }
            cost = speciesList.map { species.reproduceCost }.sum()
        }
        return Pair(speciesList, cost)
    }

    override fun getType(): ReproduceStrategyType {
        return ReproduceStrategyType.PARAMETRIZED_PROBABILITY
    }
}
