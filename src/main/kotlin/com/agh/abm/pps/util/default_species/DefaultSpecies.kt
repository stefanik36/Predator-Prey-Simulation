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
            restEnergyConsumption = 30.0,
            consumeRange = 0.0,
            canConsume = listOf(),

            //energyTransferParameter
            minEnergy = 0.0,
            maxEnergy = 1000.0,
            energy = 1000.0,
            alive = true,

            //movementParameter
            currentPosition = VectorFactory.zero(),
            moveCost = 0.0,
            moveMaxDistance = 0.0,

            //reproduceParameter
            reproduceThreshold = 200.0,
            reproduceCost = 100.0,
            reproduceProbability = 0.3,
            maxNumberOfOffspring = 3,
            reproduceRange = 30.0,
            reproduceMultiplyEnergy = 4.1,
            reproduceAddEnergy = 100.0,
            maxNumberOfSpecies = 5_000,

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
            maxConsumption = 300.0,
            restEnergyConsumption = 40.0,
            consumeRange = 10.0,
            canConsume = listOf(SpeciesType.GRASS),

            //energyTransferParameter
            minEnergy = 0.0,
            maxEnergy = 500.0,
            energy = 200.0,
            alive = true,

            //movementParameter
            currentPosition = VectorFactory.zero(),
            moveCost = 100.0,
            moveMaxDistance = 50.0,

            //reproduceParameter
            reproduceThreshold = 110.0,
            reproduceCost = 200.0,
            reproduceProbability = 0.4,
            maxNumberOfOffspring = 5,
            reproduceRange = 10.0,
            reproduceMultiplyEnergy = 0.1,
            reproduceAddEnergy = 0.0,
            maxNumberOfSpecies = 500,

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
            maxConsumption = 450.0,
            restEnergyConsumption = 10.0,
            consumeRange = 15.0,
            canConsume = listOf(SpeciesType.PREY),

            //energyTransferParameter
            minEnergy = 0.0,
            maxEnergy = 800.0,
            energy = 100.0,
            alive = true,

            //movementParameter
            currentPosition = VectorFactory.zero(),
            moveCost = 50.0,
            moveMaxDistance = 40.0,

            //reproduceParameter
            reproduceThreshold = 300.0,
            reproduceCost = 100.0,
            reproduceProbability = 0.7,
            maxNumberOfOffspring = 5,
            reproduceRange = 15.0,
            reproduceMultiplyEnergy = 0.1,
            reproduceAddEnergy = 0.0,
            maxNumberOfSpecies = 4_000,

            //guiParameter
            size = 8.0
        )
    }
}



