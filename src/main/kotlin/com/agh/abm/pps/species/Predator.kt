package com.agh.abm.pps.species

import com.agh.abm.pps.MovementStrategy
import com.agh.abm.pps.Vector
import com.agh.abm.pps.RandomMovementStrategy

class Predator(
    currentPosition: Vector,
    movementStrategy: MovementStrategy,
    energy: Double,
    moveCost: Double,
    moveMaxDistance: Double,
    size: Double,
    range: Double
) : Species(currentPosition, movementStrategy, energy, moveCost, moveMaxDistance, size, range) {

    override fun getOverview(): String {
        return "Predator: [${currentPosition.x};${currentPosition.y}]"
    }

    companion object {
        fun standard(position: Vector): Predator {
            return Predator(position, RandomMovementStrategy(), 100.0, 1.0, 3.0, 15.0, 50.0)
        }
    }


}