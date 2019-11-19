package com.agh.abm.pps.model.parameter

import kotlin.math.min

data class EnergyTransferParameter(
    var minEnergy: Double,
    var maxEnergy: Double,
    var inEnergy: Double,
    var alive: Boolean
) {
    var energy: Double = min(inEnergy, maxEnergy)
        set(value) {
            field = min(value, maxEnergy)
        }
}
