package com.agh.abm.pps.strategy.movement

import com.agh.abm.pps.model.board.Area
import com.agh.abm.pps.model.parameter.MovementParameter
import com.agh.abm.pps.util.geometric.Vector

class NoMovementStrategy : MovementStrategy {

    override fun getNextPosition(
        movementParameter: MovementParameter,
        area: Area
    ): Vector {
        return movementParameter.currentPosition
    }

    override fun getConsumedEnergy(movementParameter: MovementParameter, distance: Double): Double {
        return movementParameter.moveCost
    }

    override fun getType(): MovementStrategyType {
        return MovementStrategyType.NO_MOVEMENT_STRATEGY
    }
}
