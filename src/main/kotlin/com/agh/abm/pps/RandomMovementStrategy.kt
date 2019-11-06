package com.agh.abm.pps

import kotlin.random.Random

class RandomMovementStrategy : MovementStrategy {


    override fun getNextPosition(maxDistance: Double, currentPosition: Vector): Vector {

        val angle = Random.nextDouble(0.0, 2 * Math.PI)
        val distance = Random.nextDouble(0.0, maxDistance)

        val shift = Vector.of(angle, distance)

        return currentPosition.add(shift)
    }
}