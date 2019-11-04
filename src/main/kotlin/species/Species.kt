package species

import Vector
import MovementStrategy

abstract class Species(
    var currentPosition: Vector,
    protected val movementStrategy: MovementStrategy,
    protected val energy: Double,
    protected val moveCost: Double,
    protected val moveMaxDistance: Double
) {

    fun move() {
        val nextPosition = movementStrategy.getNextPosition(moveMaxDistance, currentPosition)
        currentPosition = nextPosition
    }

    abstract fun getOverview(): String

}