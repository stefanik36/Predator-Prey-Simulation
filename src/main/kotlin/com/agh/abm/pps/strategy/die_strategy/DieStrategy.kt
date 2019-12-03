package com.agh.abm.pps.strategy.die_strategy

import com.agh.abm.pps.model.parameter.EnergyTransferParameter

enum class DieStrategyType(name: String, val strategy: DieStrategy) {
    TO_LOW_ENERGY("To low energy", TooLowEnergyDieStrategy());

    override fun toString(): String {
        return name
    }
}

interface DieStrategy {
    fun checkIfShouldDie(energyTransferParameter: EnergyTransferParameter): Boolean

    fun getType(): DieStrategyType
}
