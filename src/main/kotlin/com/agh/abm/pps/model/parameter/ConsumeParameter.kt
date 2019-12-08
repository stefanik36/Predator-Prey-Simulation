package com.agh.abm.pps.model.parameter


data class ConsumeParameter(
    var maxConsumption: Double,
    var restEnergyConsumption: Double,
    var consumeRange: Double,
    var canConsume: MutableList<String>
) {
}
