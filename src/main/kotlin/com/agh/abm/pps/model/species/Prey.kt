package com.agh.abm.pps.model.species

import com.agh.abm.pps.movement.EnergyTransferStrategy
import com.agh.abm.pps.movement.MovementStrategy
import com.agh.abm.pps.movement.RandomMovementStrategy
import com.agh.abm.pps.util.geometric.Vector

class Prey(
    currentPosition: Vector,
    movementStrategy: MovementStrategy,
    energyTransferStrategy: EnergyTransferStrategy,
    eats: List<SpeciesType>,
    alive: Boolean,
    minEnergy: Double,
    maxEnergy: Double,
    energy: Double,
    maxTransfer: Double,
    energyConsume: Double,
    moveCost: Double,
    moveMaxDistance: Double,
    size: Double,
    range: Double
) : Species(
    currentPosition,
    movementStrategy,
    energyTransferStrategy,
    eats,
    alive,
    minEnergy,
    maxEnergy,
    energy,
    maxTransfer,
    energyConsume,
    moveCost,
    moveMaxDistance,
    size,
    range
) {
    private val speciesType: SpeciesType = SpeciesType.PREY

    override fun getType(): SpeciesType {
        return speciesType;
    }
}