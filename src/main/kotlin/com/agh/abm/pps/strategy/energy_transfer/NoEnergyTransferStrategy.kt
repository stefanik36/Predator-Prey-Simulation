package com.agh.abm.pps.strategy.energy_transfer

import com.agh.abm.pps.model.species.Species

class NoEnergyTransferStrategy : EnergyTransferStrategy {

    override fun transfer(species: Species): Double? {
        return null
    }

    override fun getType(): EnergyTransferStrategyType {
        return EnergyTransferStrategyType.NONE
    }
}
