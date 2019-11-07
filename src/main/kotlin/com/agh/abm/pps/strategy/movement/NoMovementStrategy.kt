package com.agh.abm.pps.strategy.movement

import com.agh.abm.pps.util.factory.VectorFactory
import com.agh.abm.pps.util.geometric.Vector
import kotlin.random.Random

class NoMovementStrategy : MovementStrategy {

    override fun getNextPosition(maxDistance: Double, currentPosition: Vector): Vector {
        return currentPosition
    }

    override fun getConsumedEnergy(distance: Double, moveCost: Double): Double {
        return moveCost
    }
}