package com.agh.abm.pps

interface MovementStrategy {

    fun getNextPosition(maxDistance: Double, currentPosition: Vector): Vector
}