package com.agh.abm.pps.movement

import com.agh.abm.pps.util.geometric.Vector
import kotlin.random.Random

class RandomMovementStrategy() : MovementStrategy {
    private var random: Random = Random

    constructor(random: Random) : this() {
        this.random = random;
    }

    override fun getNextPosition(maxDistance: Double, currentPosition: Vector): Vector {

        val angle = random.nextDouble(0.0, 2 * Math.PI)
        val distance = random.nextDouble(0.0, maxDistance)

        val shift = Vector.of(angle, distance)

        return currentPosition.add(shift)
    }

    override fun getConsumedEnergy(distance: Double, moveCost: Double): Double {
        return moveCost
    }
}