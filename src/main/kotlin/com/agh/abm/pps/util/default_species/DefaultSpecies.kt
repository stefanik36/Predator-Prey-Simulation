package com.agh.abm.pps.util.default_species

import com.agh.abm.pps.model.parameter.SpeciesParameter
import com.agh.abm.pps.model.species.SpeciesType
import com.agh.abm.pps.strategy.die_strategy.TooLowEnergyDieStrategy
import com.agh.abm.pps.strategy.energy_transfer.GetFromAllEnergyTransferStrategy
import com.agh.abm.pps.strategy.energy_transfer.NoEnergyTransferStrategy
import com.agh.abm.pps.strategy.movement.NoMovementStrategy
import com.agh.abm.pps.strategy.movement.RandomMovementStrategy
import com.agh.abm.pps.strategy.reproduce.ParametrizedProbabilityReproduceStrategy
import com.agh.abm.pps.util.factory.VectorFactory

class DefaultSpecies {
    companion object{
        val grassParameters: SpeciesParameter = SpeciesParameter(
            type = SpeciesType.GRASS,
            movementStrategy = NoMovementStrategy(),
            energyTransferStrategy = NoEnergyTransferStrategy(),
            reproduceStrategy = ParametrizedProbabilityReproduceStrategy(),
            dieStrategy = TooLowEnergyDieStrategy(),

            //consumeParameter
            maxConsumption = 0.0,
            restEnergyConsumption = 0.0,
            consumeRange = 0.0,
            canConsume = listOf(),

            //energyTransferParameter
            minEnergy = 0.0,
            maxEnergy = 5.0,
            energy = 5.0,
            alive = true,

            //movementParameter
            currentPosition = VectorFactory.zero(),
            moveCost = 0.0,
            moveMaxDistance = 0.0,

            //reproduceParameter
            reproduceThreshold = 1.5,
            reproduceCost = 0.5,
            reproduceProbability = 0.2,
            maxNumberOfOffspring = 3,
            reproduceRange = 1.0,
            reproduceMultiplyEnergy = 4.0,
            reproduceAddEnergy = 0.5,

            //guiParameter
            size = 2.0
        )

        val preyParameters: SpeciesParameter = SpeciesParameter(
            type = SpeciesType.PREY,

            movementStrategy = RandomMovementStrategy(),
            energyTransferStrategy = GetFromAllEnergyTransferStrategy(),
            reproduceStrategy = ParametrizedProbabilityReproduceStrategy(),
            dieStrategy = TooLowEnergyDieStrategy(),

            //consumeParameter
            maxConsumption = 1.0,
            restEnergyConsumption = 0.1,
            consumeRange = 4.0,
            canConsume = listOf(SpeciesType.GRASS),

            //energyTransferParameter
            minEnergy = 0.0,
            maxEnergy = 40.0,
            energy = 10.0,
            alive = true,

            //movementParameter
            currentPosition = VectorFactory.zero(),
            moveCost = 1.0,
            moveMaxDistance = 10.0,

            //reproduceParameter
            reproduceThreshold = 11.0,
            reproduceCost = 4.0,
            reproduceProbability = 0.4,
            maxNumberOfOffspring = 3,
            reproduceRange = 10.0,
            reproduceMultiplyEnergy = 1.5,
            reproduceAddEnergy = 0.0,

            //guiParameter
            size = 4.0
        )

        val predatorParameters: SpeciesParameter = SpeciesParameter(
            type = SpeciesType.PREDATOR,

            movementStrategy = RandomMovementStrategy(),
            energyTransferStrategy = GetFromAllEnergyTransferStrategy(),
            reproduceStrategy = ParametrizedProbabilityReproduceStrategy(),
            dieStrategy = TooLowEnergyDieStrategy(),

            //consumeParameter
            maxConsumption = 5.0,
            restEnergyConsumption = 0.05,
            consumeRange = 8.0,
            canConsume = listOf(SpeciesType.PREY),

            //energyTransferParameter
            minEnergy = 0.0,
            maxEnergy = 20.0,
            energy = 10.0,
            alive = true,

            //movementParameter
            currentPosition = VectorFactory.zero(),
            moveCost = 0.5,
            moveMaxDistance = 20.0,

            //reproduceParameter
            reproduceThreshold = 15.0,
            reproduceCost = 8.0,
            reproduceProbability = 0.7,
            maxNumberOfOffspring = 5,
            reproduceRange = 15.0,
            reproduceMultiplyEnergy = 1.3,
            reproduceAddEnergy = 0.0,

            //guiParameter
            size = 8.0
        )
    }
}



