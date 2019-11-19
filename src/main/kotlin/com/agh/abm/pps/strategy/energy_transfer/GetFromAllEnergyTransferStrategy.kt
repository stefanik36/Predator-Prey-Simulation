package com.agh.abm.pps.strategy.energy_transfer

import com.agh.abm.pps.model.parameter.ConsumeParameter
import com.agh.abm.pps.model.species.Species

class GetFromAllEnergyTransferStrategy : EnergyTransferStrategy {

    override fun transfer(food: List<Species>, consumeParameter: ConsumeParameter): Double {
        return food.map { s -> s.takeEnergy(consumeParameter.maxConsumption / food.size) }.sum();
    }

    override fun getType(): EnergyTransferStrategyType {
        return EnergyTransferStrategyType.GET_FROM_ALL
    }
}
