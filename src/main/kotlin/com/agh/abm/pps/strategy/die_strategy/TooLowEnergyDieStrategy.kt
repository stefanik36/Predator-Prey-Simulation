package com.agh.abm.pps.strategy.die_strategy

import com.agh.abm.pps.model.parameter.ConsumeParameter
import com.agh.abm.pps.model.parameter.EnergyTransferParameter
import com.agh.abm.pps.model.species.Species

class TooLowEnergyDieStrategy : DieStrategy {

    override fun checkIfShouldDie(energyTransferParameter: EnergyTransferParameter): Boolean {
        return energyTransferParameter.energy <= energyTransferParameter.minEnergy
    }
}
