package com.agh.abm.pps.strategy.movement

import com.agh.abm.pps.model.board.Area
import com.agh.abm.pps.model.parameter.MovementParameter
import com.agh.abm.pps.util.factory.VectorFactory
import com.agh.abm.pps.util.geometric.Vector
import kotlin.random.Random

class RandomMovementStrategy() : MovementStrategy {

    private var random: Random = Random

    constructor(random: Random) : this() {
        this.random = random;
    }

    override fun getNextPosition(
        movementParameter: MovementParameter,
        area: Area
    ): Vector {
        val shift = VectorFactory.random(random, movementParameter.moveMaxDistance)

        return movementParameter.currentPosition.addWithRestriction(shift, area.restriction)
    }

    override fun getConsumedEnergy(movementParameter: MovementParameter, distance: Double): Double {
        return movementParameter.moveCost
    }

    override fun getType(): MovementStrategyType {
        return MovementStrategyType.RANDOM_MOVEMENT_STRATEGY
    }
}
