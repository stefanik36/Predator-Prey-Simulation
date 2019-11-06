package com.agh.abm.pps.species

import com.agh.abm.pps.MovementStrategy
import com.agh.abm.pps.RandomMovementStrategy
import com.agh.abm.pps.Vector


class Prey(
    currentPosition: Vector,
    movementStrategy: MovementStrategy,
    energy: Double,
    moveCost: Double,
    moveMaxDistance: Double,
    size: Double,
    range: Double
): Species(currentPosition, movementStrategy, energy, moveCost, moveMaxDistance, size, range) {
    override fun getOverview(): String {
        return "PREY"
    }

    companion object {
        fun standard(position: Vector): Prey{
            return Prey(position, RandomMovementStrategy(), 100.0, 1.0, 3.0, 10.0, 50.0)
        }
    }
}