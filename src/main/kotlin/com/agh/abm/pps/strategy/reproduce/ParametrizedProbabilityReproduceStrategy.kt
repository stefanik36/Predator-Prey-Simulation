package com.agh.abm.pps.strategy.reproduce

import com.agh.abm.pps.model.board.Area
import com.agh.abm.pps.model.parameter.MovementParameter
import com.agh.abm.pps.model.parameter.ReproduceParameter
import com.agh.abm.pps.model.species.Species
import com.agh.abm.pps.util.factory.VectorFactory
import com.agh.abm.pps.util.geometric.PositionRestriction
import com.agh.abm.pps.util.geometric.Vector
import kotlin.random.Random

class ParametrizedProbabilityReproduceStrategy() : ReproduceStrategy {
    private var random: Random = Random

    constructor(random: Random) : this() {
        this.random = random;
    }

    override fun reproduce(species: Species, area: Area): Pair<List<Species>, Double> {
        val reproduceParameter = species.reproduceParameter

        if (random.nextDouble() > reproduceParameter.reproduceProbability) {
            return Pair(listOf(), 0.0)
        }
        if (reproduceParameter.maxNumberOfSpecies < area.countSpecies(species.getType())) {
            return Pair(listOf(), 0.0)
        }


//        val reproduceConditions = listOf(
//            random.nextDouble() <= reproduceParameter.reproduceProbability,
//            reproduceParameter.maxNumberOfSpecies >= area.countSpecies(species.getType())
//        )
//
//        if (!reproduceConditions.all { it }) {
//            return Pair(listOf(), 0.0)
//        }

        val numberOfOffspring =
            if (reproduceParameter.maxNumberOfOffspring == 1) 1
            else random.nextInt(1, reproduceParameter.maxNumberOfOffspring)

        val offspringList = IntArray(numberOfOffspring) { it }
            .map {
                val offspringPosition = computeOffspringPosition(
                    reproduceParameter,
                    species.movementParameter,
                    area.restriction
                )
                val offspringEnergy = computeEnergy(reproduceParameter)
                species.generate(offspringPosition, offspringEnergy)
            }
        val cost = offspringList.map { reproduceParameter.reproduceCost }.sum()

        return Pair(offspringList, cost)
    }

    private fun computeEnergy(reproduceParameter: ReproduceParameter) =
        reproduceParameter.reproduceCost * reproduceParameter.reproduceMultiplyEnergy + reproduceParameter.reproduceAddEnergy

    private fun computeOffspringPosition(
        reproduceParameter: ReproduceParameter,
        movementParameter: MovementParameter,
        restriction: PositionRestriction
    ): Vector {
        val shift = VectorFactory.random(random, reproduceParameter.reproduceRange)
        return shift.addWithRestriction(movementParameter.currentPosition, restriction)
    }

    override fun getType(): ReproduceStrategyType {
        return ReproduceStrategyType.PARAMETRIZED_PROBABILITY
    }
}
