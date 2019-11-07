package com.agh.abm.pps.strategy.movement

import com.agh.abm.pps.util.factory.VectorFactory
import com.agh.abm.pps.util.geometric.Vector
import kotlin.random.Random

class RandomMovementStrategy() : MovementStrategy {
    private var random: Random = Random

    constructor(random: Random) : this() {
        this.random = random;
    }

    override fun getNextPosition(maxDistance: Double, currentPosition: Vector): Vector {
        val shift = VectorFactory.random(random, maxDistance)
        return currentPosition.add(shift)
    }

    override fun getConsumedEnergy(distance: Double, moveCost: Double): Double {
        return moveCost
    }
}