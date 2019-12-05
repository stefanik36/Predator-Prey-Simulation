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
            dieStrategies = listOf(TooLowEnergyDieStrategy()),

            //consumeParameter
            maxConsumption = 0.0,
            restEnergyConsumption = 30.0,
            consumeRange = 0.0,
            canConsume = listOf(),

            //energyTransferParameter
            minEnergy = 0.0,
            maxEnergy = 350.0,
            energy = 350.0,
            alive = true,

            //movementParameter
            currentPosition = VectorFactory.zero(),
            moveCost = 0.0,
            moveMaxDistance = 0.0,

            //reproduceParameter
            reproduceThreshold = 220.0,
            reproduceCost = 100.0,
            reproduceProbability = 0.3,
            maxNumberOfOffspring = 3,
            reproduceRange = 30.0,
            reproduceMultiplyEnergy = 2.5,
            reproduceAddEnergy = 100.0,
            maxNumberOfSpecies = 1_000_000_000,
            reproduceDensityLimit = 40,
            //guiParameter
            size = 3.0
        )

        val preyParameters: SpeciesParameter = SpeciesParameter(
            type = SpeciesType.PREY,

            movementStrategy = RandomMovementStrategy(),
            energyTransferStrategy = GetFromAllEnergyTransferStrategy(),
            reproduceStrategy = ParametrizedProbabilityReproduceStrategy(),
            dieStrategies = listOf(TooLowEnergyDieStrategy()),

            //consumeParameter
            maxConsumption = 200.0,
            restEnergyConsumption = 20.0,
            consumeRange = 15.0,
            canConsume = listOf(SpeciesType.GRASS),

            //energyTransferParameter
            minEnergy = 0.0,
            maxEnergy = 1000.0,
            energy = 400.0,
            alive = true,

            //movementParameter
            currentPosition = VectorFactory.zero(),
            moveCost = 40.0,
            moveMaxDistance = 30.0,

            //reproduceParameter
            reproduceThreshold = 840.0,
            reproduceCost = 800.0,
            reproduceProbability = 0.4,
            maxNumberOfOffspring = 4,
            reproduceRange = 10.0,
            reproduceMultiplyEnergy = 0.1,
            reproduceAddEnergy = 0.0,
            maxNumberOfSpecies = 1_000_000,
            reproduceDensityLimit = 1_000_000,

            //guiParameter
            size = 8.0
        )

        val predatorParameters: SpeciesParameter = SpeciesParameter(
            type = SpeciesType.PREDATOR,

            movementStrategy = RandomMovementStrategy(),
            energyTransferStrategy = GetFromAllEnergyTransferStrategy(),
            reproduceStrategy = ParametrizedProbabilityReproduceStrategy(),
            dieStrategies = listOf(TooLowEnergyDieStrategy()),

            //consumeParameter
            maxConsumption = 400.0,
            restEnergyConsumption = 10.0,
            consumeRange = 15.0,
            canConsume = listOf(SpeciesType.PREY),

            //energyTransferParameter
            minEnergy = 0.0,
            maxEnergy = 600.0,
            energy = 300.0,
            alive = true,

            //movementParameter
            currentPosition = VectorFactory.zero(),
            moveCost = 10.0,
            moveMaxDistance = 40.0,

            //reproduceParameter
            reproduceThreshold = 300.0,
            reproduceCost = 100.0,
            reproduceProbability = 0.5,
            maxNumberOfOffspring = 5,
            reproduceRange = 15.0,
            reproduceMultiplyEnergy = 0.1,
            reproduceAddEnergy = 0.0,
            maxNumberOfSpecies = 1_000_000,
            reproduceDensityLimit = 1_000_000,

            //guiParameter
            size = 8.0
        )
    }
}



