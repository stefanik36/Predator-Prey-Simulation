package com.agh.abm.pps.strategy

import com.agh.abm.pps.model.board.Area
import com.agh.abm.pps.strategy.movement.RandomMovementStrategy
import com.agh.abm.pps.strategy.reproduce.ParametrizedProbabilityReproduceStrategy
import com.agh.abm.pps.util.factory.SpeciesFactory
import com.agh.abm.pps.util.geometric.Vector
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.random.Random

class ProbabilityReproduceStrategyTest {

    @Test
    fun probabilityReproduceStrategy() {
        val strategy = ParametrizedProbabilityReproduceStrategy()

        val prey01 = SpeciesFactory.standardPrey(
            Vector(5.0, 6.0),
            RandomMovementStrategy(Random(666)),
            ParametrizedProbabilityReproduceStrategy(Random(666)),
            8.0,
            3.0
        )
        prey01.reproduceParameter.reproduceMultiplyEnergy=1.0

        val result = strategy.reproduce(prey01, Area(mutableListOf()))

        Assertions.assertEquals(1, result.first.size)
        Assertions.assertEquals(3.0, result.first[0].energyTransferParameter.energy)
        Assertions.assertEquals(3.0, result.second)
    }
}
