package com.agh.abm.pps.strategy

import com.agh.abm.pps.strategy.energy_transfer.GetFromAllEnergyTransferStrategy
import com.agh.abm.pps.strategy.movement.RandomMovementStrategy
import com.agh.abm.pps.util.factory.SpeciesFactory
import com.agh.abm.pps.util.geometric.Vector
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.random.Random

class GetFromAllEnergyTransferStrategyTest {

    @Test
    fun getFromAllEnergyTransferStrategyTransfer() {
        val transferStrategy = GetFromAllEnergyTransferStrategy()

        val prey01 = SpeciesFactory.standardPrey(
            Vector(5.0, 6.0),
            Random(666),
            4.0
        )
        val prey02 = SpeciesFactory.standardPrey(
            Vector(0.0, 1.0),
            Random(666),
            10.0
        )

        val result = transferStrategy.transfer(listOf(prey01, prey02), 10.0)

        Assertions.assertEquals(0.0, prey01.energy)
        Assertions.assertEquals(5.0, prey02.energy)
        Assertions.assertEquals(9.0, result)
    }
}