package com.agh.abm.pps.strategy.energy_transfer

import com.agh.abm.pps.model.species.Species

enum class EnergyTransferStrategyType(name: String, val strategy: EnergyTransferStrategy){
    GET_FROM_ALL("Get from all", GetFromAllEnergyTransferStrategy()),
    NONE("None", GetFromAllEnergyTransferStrategy());

    override fun toString(): String {
        return name
    }}

interface EnergyTransferStrategy {
    fun transfer(species: Species): Double?
    fun getType(): EnergyTransferStrategyType
}
