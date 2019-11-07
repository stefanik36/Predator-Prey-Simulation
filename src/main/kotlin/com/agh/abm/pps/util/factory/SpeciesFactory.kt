package com.agh.abm.pps.util.factory

import com.agh.abm.pps.model.species.Predator
import com.agh.abm.pps.model.species.Prey
import com.agh.abm.pps.model.species.SpeciesType
import com.agh.abm.pps.movement.GetFromAllEnergyTransferStrategy
import com.agh.abm.pps.movement.MovementStrategy
import com.agh.abm.pps.movement.RandomMovementStrategy
import com.agh.abm.pps.util.geometric.Vector

class SpeciesFactory {

    companion object {
        fun standardPrey(position: Vector, movementStrategy: MovementStrategy, energy: Double): Prey {
            return Prey(
                position,
                movementStrategy, GetFromAllEnergyTransferStrategy(),
                listOf(), true,
                0.0, 50.0, energy, 1.0, 0.5,
                0.5, 2.0, 5.0, 2.0
            )
        }

        fun standardPrey(position: Vector, movementStrategy: MovementStrategy): Prey {
            return standardPrey(position, movementStrategy, 10.0)
        }

        fun standardPrey(position: Vector): Prey {
            return standardPrey(position, RandomMovementStrategy());
        }


        fun standardPredator(position: Vector, movementStrategy: MovementStrategy): Predator {
            return Predator(
                position,
                movementStrategy, GetFromAllEnergyTransferStrategy(),
                listOf(SpeciesType.PREY), true,
                0.0, 100.0, 10.0, 3.0, 1.0,
                1.0, 2.0, 10.0, 5.0
            )
        }

        fun standardPredator(position: Vector): Predator {
            return standardPredator(position, RandomMovementStrategy())
        }


    }
}