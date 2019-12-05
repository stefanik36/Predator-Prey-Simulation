package com.agh.abm.pps.util.factory

import com.agh.abm.pps.model.parameter.*
import com.agh.abm.pps.model.species.Grass
import com.agh.abm.pps.model.species.Predator
import com.agh.abm.pps.model.species.Prey
import com.agh.abm.pps.model.species.SpeciesType
import com.agh.abm.pps.strategy.die_strategy.TooLowEnergyDieStrategy
import com.agh.abm.pps.strategy.energy_transfer.GetFromAllEnergyTransferStrategy
import com.agh.abm.pps.strategy.movement.MovementStrategy
import com.agh.abm.pps.strategy.movement.NoMovementStrategy
import com.agh.abm.pps.strategy.movement.RandomMovementStrategy
import com.agh.abm.pps.strategy.reproduce.ParametrizedProbabilityReproduceStrategy
import com.agh.abm.pps.strategy.reproduce.ReproduceStrategy
import com.agh.abm.pps.util.default_species.DefaultSpecies
import com.agh.abm.pps.util.geometric.Vector
import kotlin.random.Random

class SpeciesFactory {

    companion object {
        fun standardGrass(
            position: Vector,
            movementStrategy: MovementStrategy,
            reproduceStrategy: ReproduceStrategy
        ): Grass {
            return Grass(
                movementStrategy = movementStrategy,
                energyTransferStrategy = GetFromAllEnergyTransferStrategy(),
                reproduceStrategy = reproduceStrategy,
                dieStrategies = listOf(TooLowEnergyDieStrategy()),
                consumeParameter = ConsumeParameter(
                    maxConsumption = DefaultSpecies.grassParameters.maxConsumption,
                    restEnergyConsumption = DefaultSpecies.grassParameters.restEnergyConsumption,
                    consumeRange = DefaultSpecies.grassParameters.consumeRange,
                    canConsume = DefaultSpecies.grassParameters.canConsume
                ),
                energyTransferParameter = EnergyTransferParameter(
                    minEnergy = DefaultSpecies.grassParameters.minEnergy,
                    maxEnergy = DefaultSpecies.grassParameters.maxEnergy,
                    inEnergy = DefaultSpecies.grassParameters.energy,
                    alive = DefaultSpecies.grassParameters.alive
                ),
                movementParameter = MovementParameter(
                    currentPosition = position,
                    moveCost = DefaultSpecies.grassParameters.moveCost,
                    moveMaxDistance = DefaultSpecies.grassParameters.moveMaxDistance
                ),
                reproduceParameter = ReproduceParameter(
                    reproduceThreshold = DefaultSpecies.grassParameters.reproduceThreshold,
                    reproduceCost = DefaultSpecies.grassParameters.reproduceCost,
                    reproduceProbability = DefaultSpecies.grassParameters.reproduceProbability,
                    maxNumberOfOffspring = DefaultSpecies.grassParameters.maxNumberOfOffspring,
                    reproduceRange = DefaultSpecies.grassParameters.reproduceRange,
                    reproduceMultiplyEnergy = DefaultSpecies.grassParameters.reproduceMultiplyEnergy,
                    reproduceAddEnergy = DefaultSpecies.grassParameters.reproduceAddEnergy,
                    maxNumberOfSpecies = DefaultSpecies.grassParameters.maxNumberOfSpecies,
                    reproduceDensityLimit = DefaultSpecies.grassParameters.reproduceDensityLimit
                ),
                guiParameter = GuiParameter(
                    size = DefaultSpecies.grassParameters.size
                )
            )
        }

        fun standardGrass(position: Vector, random: Random): Grass {
            return standardGrass(
                position = position,
                movementStrategy = DefaultSpecies.grassParameters.movementStrategy,
                reproduceStrategy = ParametrizedProbabilityReproduceStrategy(random)
            )
        }

        fun standardGrass(random: Random): Grass {
            return standardGrass(
                position = VectorFactory.random(random, 20.0),
                movementStrategy = DefaultSpecies.grassParameters.movementStrategy,
                reproduceStrategy = ParametrizedProbabilityReproduceStrategy(random)
            )
        }

        fun standardPrey(
            position: Vector,
            movementStrategy: MovementStrategy,
            reproduceStrategy: ReproduceStrategy,
            energy: Double,
            reproduceCost: Double
        ): Prey {
            return Prey(
                movementStrategy = movementStrategy,
                energyTransferStrategy = GetFromAllEnergyTransferStrategy(),
                reproduceStrategy = reproduceStrategy,
                dieStrategies = listOf(TooLowEnergyDieStrategy()),
                consumeParameter = ConsumeParameter(
                    maxConsumption = DefaultSpecies.preyParameters.maxConsumption,
                    restEnergyConsumption = DefaultSpecies.preyParameters.restEnergyConsumption,
                    consumeRange = DefaultSpecies.preyParameters.consumeRange,
                    canConsume = DefaultSpecies.preyParameters.canConsume
                ),
                energyTransferParameter = EnergyTransferParameter(
                    minEnergy = DefaultSpecies.preyParameters.minEnergy,
                    maxEnergy = DefaultSpecies.preyParameters.maxEnergy,
                    inEnergy = energy,
                    alive = DefaultSpecies.preyParameters.alive
                ),
                movementParameter = MovementParameter(
                    currentPosition = position,
                    moveCost = DefaultSpecies.preyParameters.moveCost,
                    moveMaxDistance = DefaultSpecies.preyParameters.moveMaxDistance
                ),
                reproduceParameter = ReproduceParameter(
                    reproduceThreshold = DefaultSpecies.preyParameters.reproduceThreshold,
                    reproduceCost = reproduceCost,
                    reproduceProbability = DefaultSpecies.preyParameters.reproduceProbability,
                    maxNumberOfOffspring = DefaultSpecies.preyParameters.maxNumberOfOffspring,
                    reproduceRange = DefaultSpecies.preyParameters.reproduceRange,
                    reproduceMultiplyEnergy = DefaultSpecies.preyParameters.reproduceMultiplyEnergy,
                    reproduceAddEnergy = DefaultSpecies.preyParameters.reproduceAddEnergy,
                    maxNumberOfSpecies = DefaultSpecies.preyParameters.maxNumberOfSpecies,
                    reproduceDensityLimit = DefaultSpecies.preyParameters.reproduceDensityLimit
                ),
                guiParameter = GuiParameter(
                    size = DefaultSpecies.preyParameters.size
                )
            )
        }

        fun standardPrey(position: Vector, random: Random, energy: Double): Prey {
            return standardPrey(
                position,
                RandomMovementStrategy(random),
                ParametrizedProbabilityReproduceStrategy(random),
                energy,
                DefaultSpecies.preyParameters.reproduceCost
            )
        }

        fun standardPrey(position: Vector, random: Random): Prey {
            return standardPrey(
                position,
                RandomMovementStrategy(random),
                ParametrizedProbabilityReproduceStrategy(random),
                DefaultSpecies.preyParameters.energy,
                DefaultSpecies.preyParameters.reproduceCost
            )
        }

        fun standardPrey(position: Vector): Prey {
            return standardPrey(
                position,
                RandomMovementStrategy(),
                ParametrizedProbabilityReproduceStrategy(),
                DefaultSpecies.preyParameters.energy,
                DefaultSpecies.preyParameters.reproduceCost
            );
        }

        fun standardPrey(random: Random): Prey {
            return standardPrey(
                VectorFactory.random(random, 20.0),
                RandomMovementStrategy(),
                ParametrizedProbabilityReproduceStrategy(),
                DefaultSpecies.preyParameters.energy,
                DefaultSpecies.preyParameters.reproduceCost
            );
        }

        fun standardPredator(
            position: Vector,
            movementStrategy: MovementStrategy,
            reproduceStrategy: ReproduceStrategy
        ): Predator {
            return Predator(
                movementStrategy = movementStrategy,
                energyTransferStrategy = GetFromAllEnergyTransferStrategy(),
                reproduceStrategy = reproduceStrategy,
                dieStrategies = listOf(TooLowEnergyDieStrategy()),
                consumeParameter = ConsumeParameter(
                    maxConsumption = DefaultSpecies.predatorParameters.maxConsumption,
                    restEnergyConsumption = DefaultSpecies.predatorParameters.restEnergyConsumption,
                    consumeRange = DefaultSpecies.predatorParameters.consumeRange,
                    canConsume = DefaultSpecies.predatorParameters.canConsume
                ),
                energyTransferParameter = EnergyTransferParameter(
                    minEnergy = DefaultSpecies.predatorParameters.minEnergy,
                    maxEnergy = DefaultSpecies.predatorParameters.maxEnergy,
                    inEnergy = DefaultSpecies.predatorParameters.energy,
                    alive = DefaultSpecies.predatorParameters.alive
                ),
                movementParameter = MovementParameter(
                    currentPosition = position,
                    moveCost = DefaultSpecies.predatorParameters.moveCost,
                    moveMaxDistance = DefaultSpecies.predatorParameters.moveMaxDistance
                ),
                reproduceParameter = ReproduceParameter(
                    reproduceThreshold = DefaultSpecies.predatorParameters.reproduceThreshold,
                    reproduceCost = DefaultSpecies.predatorParameters.reproduceCost,
                    reproduceProbability = DefaultSpecies.predatorParameters.reproduceProbability,
                    maxNumberOfOffspring = DefaultSpecies.predatorParameters.maxNumberOfOffspring,
                    reproduceRange = DefaultSpecies.predatorParameters.reproduceRange,
                    reproduceMultiplyEnergy = DefaultSpecies.predatorParameters.reproduceMultiplyEnergy,
                    reproduceAddEnergy = DefaultSpecies.predatorParameters.reproduceAddEnergy,
                    maxNumberOfSpecies = DefaultSpecies.predatorParameters.maxNumberOfSpecies,
                    reproduceDensityLimit = DefaultSpecies.predatorParameters.reproduceDensityLimit
                ),
                guiParameter = GuiParameter(
                    size = DefaultSpecies.predatorParameters.size
                )
            )
        }

        fun standardPredator(position: Vector, random: Random): Predator {
            return standardPredator(
                position,
                RandomMovementStrategy(random),
                ParametrizedProbabilityReproduceStrategy(random)
            )
        }

        fun standardPredator(position: Vector): Predator {
            return standardPredator(position, RandomMovementStrategy(), ParametrizedProbabilityReproduceStrategy())
        }

        fun standardPredator(random: Random): Predator {
            return standardPredator(
                VectorFactory.random(random, 20.0),
                RandomMovementStrategy(random),
                ParametrizedProbabilityReproduceStrategy(random)
            )
        }
    }
}
