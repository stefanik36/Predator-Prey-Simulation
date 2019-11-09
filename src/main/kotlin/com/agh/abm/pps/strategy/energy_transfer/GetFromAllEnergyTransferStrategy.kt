package com.agh.abm.pps.strategy.energy_transfer

import com.agh.abm.pps.model.species.Species

class GetFromAllEnergyTransferStrategy : EnergyTransferStrategy {

    override fun transfer(food: List<Species>, maxTransfer: Double): Double {
        return food.map { s -> s.takeEnergy(maxTransfer / food.size) }.sum();
    }

    override fun getType(): EnergyTransferStrategyType {
        return EnergyTransferStrategyType.GET_FROM_ALL
    }
}