package com.agh.abm.pps.model.species

import com.agh.abm.pps.model.Area
import com.agh.abm.pps.strategy.energy_transfer.EnergyTransferStrategy
import com.agh.abm.pps.strategy.movement.MovementStrategy
import com.agh.abm.pps.strategy.reproduce.ReproduceStrategy
import com.agh.abm.pps.util.geometric.Vector

class Grass(
    currentPosition: Vector,
    movementStrategy: MovementStrategy,
    energyTransferStrategy: EnergyTransferStrategy,
    reproduceStrategy: ReproduceStrategy,
    alive: Boolean,
    minEnergy: Double,
    maxEnergy: Double,
    inEnergy: Double,
    maxConsumption: Double,
    restEnergyConsumption: Double,
    consumeRange: Double,
    eats: List<SpeciesType>,
    moveCost: Double,
    moveMaxDistance: Double,
    reproduceThreshold: Double,
    reproduceCost: Double,
    reproduceProbability: Double,
    maxNumberOfOffspring: Int,
    reproduceRange: Double,
    reproduceMultiplyEnergy: Double,
    reproduceAddEnergy: Double,
    size: Double
) : Species(
    currentPosition,
    movementStrategy,
    energyTransferStrategy,
    reproduceStrategy,
    alive,
    minEnergy,
    maxEnergy,
    inEnergy,
    maxConsumption,
    restEnergyConsumption,
    consumeRange,
    eats,
    moveCost,
    moveMaxDistance,
    reproduceThreshold,
    reproduceCost,
    reproduceProbability,
    maxNumberOfOffspring,
    reproduceRange,
    reproduceMultiplyEnergy,
    reproduceAddEnergy,
    size
) {
    private val speciesType: SpeciesType = SpeciesType.GRASS

    override fun getType(): SpeciesType {
        return speciesType;
    }

    override fun generate(currentPosition: Vector, inEnergy: Double): Species {
        return Grass(
            currentPosition,
            movementStrategy,
            energyTransferStrategy,
            reproduceStrategy,
            alive,
            minEnergy,
            maxEnergy,
            inEnergy,
            maxConsumption,
            restEnergyConsumption,
            consumeRange,
            eats,
            moveCost,
            moveMaxDistance,
            reproduceThreshold,
            reproduceCost,
            reproduceProbability,
            maxNumberOfOffspring,
            reproduceRange,
            reproduceMultiplyEnergy,
            reproduceAddEnergy,
            size
        )
    }

    override fun consume(area: Area) {
//        OPTIMIZATION
    }
}
