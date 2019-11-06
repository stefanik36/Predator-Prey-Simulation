package com.agh.abm.pps.species

import com.agh.abm.pps.Vector
import com.agh.abm.pps.MovementStrategy

abstract class Species(
    var currentPosition: Vector,
    protected val movementStrategy: MovementStrategy,
    protected val energy: Double,
    protected val moveCost: Double,
    protected val moveMaxDistance: Double,
    val size: Double,
    val range: Double
) {

    fun move() {
        val nextPosition = movementStrategy.getNextPosition(moveMaxDistance, currentPosition)
        currentPosition = nextPosition
    }

    abstract fun getOverview(): String

}