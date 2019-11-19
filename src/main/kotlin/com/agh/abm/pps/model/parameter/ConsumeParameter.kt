package com.agh.abm.pps.model.parameter

import com.agh.abm.pps.model.species.SpeciesType

data class ConsumeParameter(
    var maxConsumption: Double,
    var restEnergyConsumption: Double,
    var consumeRange: Double,
    var canConsume: List<SpeciesType>
) {
}
