package com.agh.abm.pps.util.factory

import com.agh.abm.pps.model.species.Grass
import com.agh.abm.pps.model.species.Predator
import com.agh.abm.pps.model.species.Prey
import com.agh.abm.pps.model.species.SpeciesType
import com.agh.abm.pps.strategy.energy_transfer.GetFromAllEnergyTransferStrategy
import com.agh.abm.pps.strategy.movement.MovementStrategy
import com.agh.abm.pps.strategy.movement.NoMovementStrategy
import com.agh.abm.pps.strategy.movement.RandomMovementStrategy
import com.agh.abm.pps.strategy.reproduce.ParametrizedProbabilityReproduceStrategy
import com.agh.abm.pps.strategy.reproduce.ProbabilityReproduceStrategy
import com.agh.abm.pps.strategy.reproduce.ReproduceStrategy
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
                position,
                movementStrategy, GetFromAllEnergyTransferStrategy(), reproduceStrategy,
                listOf(), true,
                0.0, 5.0, 5.0, 0.0, 0.0, 2.0,
                0.0, 0.0,
                2.0, 0.5, 0.7, 3, 50.0,
                2.0
            )
        }

        fun standardGrass(position: Vector, random: Random, multipleEnergy: Double): Grass {
            return standardGrass(
                position,
                NoMovementStrategy(),
                ParametrizedProbabilityReproduceStrategy(random, multipleEnergy)
            )
        }

        fun standardGrass(position: Vector, random: Random): Grass {
            return standardGrass(
                position,
                NoMovementStrategy(),
                ParametrizedProbabilityReproduceStrategy(random, 4.0)
            )
        }

        fun standardGrass(random: Random): Grass {
            return standardGrass(
                VectorFactory.random(random, 20.0),
                NoMovementStrategy(),
                ParametrizedProbabilityReproduceStrategy(random, 5.0)
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
                position,
                movementStrategy,
                GetFromAllEnergyTransferStrategy(), reproduceStrategy,
                listOf(SpeciesType.GRASS), true,
                0.0, 50.0, energy, 3.0, 1.0, 10.0,
                0.5, 2.0,
                5.0, reproduceCost, 0.9, 2, 100.0,
                4.0
            )
        }

        fun standardPrey(position: Vector, random: Random, energy: Double): Prey {
            return standardPrey(
                position,
                RandomMovementStrategy(random),
                ProbabilityReproduceStrategy(random),
                energy,
                3.0
            )
        }

        fun standardPrey(position: Vector, random: Random): Prey {
            return standardPrey(
                position,
                RandomMovementStrategy(random),
                ProbabilityReproduceStrategy(random),
                20.0,
                3.0
            )
        }

        fun standardPrey(position: Vector): Prey {
            return standardPrey(position, RandomMovementStrategy(), ProbabilityReproduceStrategy(), 20.0, 3.0);
        }

        fun standardPrey(random: Random): Prey {
            return standardPrey(
                VectorFactory.random(random, 20.0),
                RandomMovementStrategy(),
                ProbabilityReproduceStrategy(),
                20.0,
                3.0
            );
        }

        fun standardPredator(
            position: Vector,
            movementStrategy: MovementStrategy,
            reproduceStrategy: ReproduceStrategy
        ): Predator {
            return Predator(
                position,
                movementStrategy, GetFromAllEnergyTransferStrategy(), reproduceStrategy,
                listOf(SpeciesType.PREY), true,
                0.0, 100.0, 10.0, 2.0, 3.0, 4.0,
                1.0, 2.0,
                7.0, 3.0, 0.7, 3, 5.0,
                8.0
            )
        }

        fun standardPredator(position: Vector, random: Random): Predator {
            return standardPredator(position, RandomMovementStrategy(random), ProbabilityReproduceStrategy(random))
        }

        fun standardPredator(position: Vector): Predator {
            return standardPredator(position, RandomMovementStrategy(), ProbabilityReproduceStrategy())
        }

        fun standardPredator(random: Random): Predator {
            return standardPredator(
                VectorFactory.random(random, 20.0),
                RandomMovementStrategy(random),
                ProbabilityReproduceStrategy(random)
            )
        }
    }
}