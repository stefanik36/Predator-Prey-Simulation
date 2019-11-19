package com.agh.abm.pps.model.parameter

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class EnergyTransferParameterTest {

    @Test
    fun setEnergy() {
        val etp = EnergyTransferParameter(0.0, 5.0, 4.0, true)

        etp.energy = 8.0

        Assertions.assertEquals(5.0, etp.energy)
    }


    @Test
    fun construct() {
        val etp = EnergyTransferParameter(0.0, 5.0, 7.0, true)

        Assertions.assertEquals(5.0, etp.energy)
    }
}
