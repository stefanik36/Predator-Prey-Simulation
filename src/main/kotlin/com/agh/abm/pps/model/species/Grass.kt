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
    eats: List<SpeciesType>,
    alive: Boolean,
    minEnergy: Double,
    maxEnergy: Double,
    energy: Double,
    maxConsumption: Double,
    energyConsume: Double,
    consumeRange: Double,
    moveCost: Double,
    moveMaxDistance: Double,
    reproduceThreshold: Double,
    reproduceCost: Double,
    reproduceProbability: Double,
    maxNumberOfOffspring: Int,
    reproduceRange: Double,
    size: Double
) : Species(
    currentPosition,
    movementStrategy,
    energyTransferStrategy,
    reproduceStrategy,
    eats,
    alive,
    minEnergy,
    maxEnergy,
    energy,
    maxConsumption,
    energyConsume,
    consumeRange,
    moveCost,
    moveMaxDistance,
    reproduceThreshold,
    reproduceCost,
    reproduceProbability,
    maxNumberOfOffspring,
    reproduceRange,
    size
) {
    private val speciesType: SpeciesType = SpeciesType.GRASS

    override fun getType(): SpeciesType {
        return speciesType;
    }

    override fun generate(currentPosition: Vector, energy: Double): Species {
        return Grass(
            currentPosition,
            movementStrategy,
            energyTransferStrategy,
            reproduceStrategy,
            eats,
            alive,
            minEnergy,
            maxEnergy,
            energy,
            maxConsumption,
            restEnergyConsumption,
            consumeRange,
            moveCost,
            moveMaxDistance,
            reproduceThreshold,
            reproduceCost,
            reproduceProbability,
            maxNumberOfOffspring,
            reproduceRange,
            size
        )
    }

    override fun consume(area: Area) {
//        OPTIMIZATION
    }
}