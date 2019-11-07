package com.agh.abm.pps.strategy.movement

import com.agh.abm.pps.util.geometric.Vector

interface MovementStrategy {

    fun getNextPosition(maxDistance: Double, currentPosition: Vector): Vector
    fun getConsumedEnergy(distance: Double, moveCost: Double): Double
}