package com.agh.abm.pps.movement

import com.agh.abm.pps.model.species.Species


interface EnergyTransferStrategy {
    fun transfer(food: List<Species>, maxTransfer: Double): Double

}