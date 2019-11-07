package com.agh.abm.pps.movement

import com.agh.abm.pps.model.species.Species
import com.agh.abm.pps.util.geometric.Vector
import kotlin.random.Random

class GetFromAllEnergyTransferStrategy: EnergyTransferStrategy {
    override fun transfer(food: List<Species>, maxTransfer: Double): Double {
        return food.map { s -> s.takeEnergy(maxTransfer / food.size) }.sum();
    }
}