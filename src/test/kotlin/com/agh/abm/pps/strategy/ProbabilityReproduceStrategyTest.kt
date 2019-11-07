package com.agh.abm.pps.strategy

import com.agh.abm.pps.strategy.movement.RandomMovementStrategy
import com.agh.abm.pps.strategy.reproduce.ProbabilityReproduceStrategy
import com.agh.abm.pps.util.factory.SpeciesFactory
import com.agh.abm.pps.util.geometric.Vector
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.random.Random

class ProbabilityReproduceStrategyTest {

    @Test
    fun probabilityReproduceStrategy() {
        val strategy = ProbabilityReproduceStrategy()

        val prey01 = SpeciesFactory.standardPrey(
            Vector(5.0, 6.0),
            RandomMovementStrategy(Random(666)),
            ProbabilityReproduceStrategy(Random(666)),
            8.0,
            3.0
        )

        val result = strategy.reproduce(prey01)

        Assertions.assertEquals(1, result.first.size)
        Assertions.assertEquals(3.0, result.first[0].energy)
        Assertions.assertEquals(3.0, result.second)
    }
}