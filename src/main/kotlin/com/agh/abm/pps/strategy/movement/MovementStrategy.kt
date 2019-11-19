package com.agh.abm.pps.strategy.movement

import com.agh.abm.pps.model.parameter.MovementParameter
import com.agh.abm.pps.util.geometric.Vector

enum class MovementStrategyType(name: String, val strategy: MovementStrategy) {
    NO_MOVEMENT_STRATEGY("No movement", NoMovementStrategy()), RANDOM_MOVEMENT_STRATEGY(
        "Random movement",
        RandomMovementStrategy()
    );

    override fun toString(): String {
        return name
    }
}

interface MovementStrategy {

    fun getNextPosition(movementParameter: MovementParameter): Vector
    fun getConsumedEnergy(movementParameter: MovementParameter, distance: Double): Double

    fun getType(): MovementStrategyType
}
