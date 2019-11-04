package species

import MovementStrategy
import Vector
import RandomMovementStrategy

class Predator(
    currentPosition: Vector,
    movementStrategy: MovementStrategy,
    energy: Double,
    moveCost: Double,
    moveMaxDistance: Double
) : Species(currentPosition, movementStrategy, energy, moveCost, moveMaxDistance) {

    override fun getOverview(): String {
        return "Predator: [${currentPosition.x};${currentPosition.y}]"
    }

    companion object {
        fun standard(position: Vector): Predator {
            return Predator(position, RandomMovementStrategy(), 100.0, 1.0, 3.0)
        }
    }


}