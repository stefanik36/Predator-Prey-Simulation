package com.agh.abm.pps.model.parameter

data class ReproduceParameter(
    var reproduceThreshold: Double,
    var reproduceCost: Double,
    var reproduceProbability: Double,
    var maxNumberOfOffspring: Int,
    var reproduceRange: Double,
    var reproduceMultiplyEnergy: Double,
    var reproduceAddEnergy: Double,
    var maxNumberOfSpecies: Int
) {
}
