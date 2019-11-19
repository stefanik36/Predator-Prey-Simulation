package com.agh.abm.pps.strategy.energy_transfer

import com.agh.abm.pps.model.species.Species

class NoEnergyTransferStrategy : EnergyTransferStrategy {

    override fun transfer(food: List<Species>, maxTransfer: Double): Double {
        return 0.0;
    }

    override fun getType(): EnergyTransferStrategyType {
        return EnergyTransferStrategyType.NONE
    }
}
