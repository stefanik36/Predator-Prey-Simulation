package com.agh.abm.pps.util.builder

import com.agh.abm.pps.model.species.SpeciesType
import com.agh.abm.pps.strategy.energy_transfer.EnergyTransferStrategy
import com.agh.abm.pps.strategy.energy_transfer.NoEnergyTransferStrategy
import com.agh.abm.pps.strategy.movement.MovementStrategy
import com.agh.abm.pps.strategy.movement.NoMovementStrategy
import com.agh.abm.pps.strategy.reproduce.ParametrizedProbabilityReproduceStrategy
import com.agh.abm.pps.strategy.reproduce.ReproduceStrategy
import com.agh.abm.pps.util.factory.VectorFactory
import com.agh.abm.pps.util.geometric.Vector



class SpeciesDefault {
    companion object {
        val grassParameters = SpeciesParameters(
            VectorFactory.zero(),
            NoMovementStrategy(), NoEnergyTransferStrategy(), ParametrizedProbabilityReproduceStrategy(),
            true,
            0.0, 5.0, 5.0,
            0.0, 0.0, 0.0, listOf(),
            0.0, 0.0,
            2.0, 0.5, 0.2, 3, 50.0, 3.0, 0.1,
            2.0
            )
    }
}


data class SpeciesParameters(
    var currentPosition: Vector,

    //strategies
    val movementStrategy: MovementStrategy,
    val energyTransferStrategy: EnergyTransferStrategy,
    val reproduceStrategy: ReproduceStrategy,

    var alive: Boolean,

    //energy
    val minEnergy: Double,
    val maxEnergy: Double,
    val inEnergy: Double,

    //consume
    val maxConsumption: Double,
    val restEnergyConsumption: Double,
    val consumeRange: Double,

    val eats: List<SpeciesType>,

    //move
    val moveCost: Double,
    val moveMaxDistance: Double,

    //reproduce
    val reproduceThreshold: Double,
    val reproduceCost: Double,
    val reproduceProbability: Double,
    val maxNumberOfOffspring: Int,
    val reproduceRange: Double,
    val reproduceMultiplyEnergy: Double,
    val reproduceAddEnergy: Double,

    val size: Double
) {

}
