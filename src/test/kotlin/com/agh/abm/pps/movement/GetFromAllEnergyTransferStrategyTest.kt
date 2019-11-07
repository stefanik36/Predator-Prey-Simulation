package com.agh.abm.pps.movement

import com.agh.abm.pps.model.Area
import com.agh.abm.pps.util.factory.SpeciesFactory
import com.agh.abm.pps.util.geometric.Vector
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.random.Random

class GetFromAllEnergyTransferStrategyTest {

    @Test
    fun getFromAllEnergyTransferStrategyTransfer() {
        val transferStrategy = GetFromAllEnergyTransferStrategy()

        val prey01 = SpeciesFactory.standardPrey(Vector(5.0, 6.0), RandomMovementStrategy(Random(666)), 4.0)
        val prey02 = SpeciesFactory.standardPrey(Vector(0.0, 1.0), RandomMovementStrategy(Random(666)))

        val result = transferStrategy.transfer(listOf(prey01, prey02), 10.0)

        Assertions.assertEquals(0.0, prey01.energy)
        Assertions.assertEquals(5.0, prey02.energy)
        Assertions.assertEquals(9.0, result)
    }
}