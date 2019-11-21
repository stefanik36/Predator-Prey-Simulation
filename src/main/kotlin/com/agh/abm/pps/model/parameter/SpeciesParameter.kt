package com.agh.abm.pps.model.parameter

import com.agh.abm.pps.model.species.SpeciesType
import com.agh.abm.pps.strategy.die_strategy.DieStrategy
import com.agh.abm.pps.strategy.energy_transfer.EnergyTransferStrategy
import com.agh.abm.pps.strategy.movement.MovementStrategy
import com.agh.abm.pps.strategy.reproduce.ReproduceStrategy
import com.agh.abm.pps.util.geometric.Vector

data class SpeciesParameter(
    var type: SpeciesType,

    var movementStrategy: MovementStrategy,
    var energyTransferStrategy: EnergyTransferStrategy,
    var reproduceStrategy: ReproduceStrategy,
    var dieStrategy: DieStrategy,

    // consumeParameter: ConsumeParameter
    var maxConsumption: Double,
    var restEnergyConsumption: Double,
    var consumeRange: Double,
    var canConsume: List<SpeciesType>,

    // energyTransferParameter: EnergyTransferParameter
    var minEnergy: Double,
    var maxEnergy: Double,
    var energy: Double,
    var alive: Boolean,

    // movementParameter: MovementParameter
    var currentPosition: Vector,
    var moveCost: Double,
    var moveMaxDistance: Double,

    // reproduceParameter: ReproduceParameter
    var reproduceThreshold: Double,
    var reproduceCost: Double,
    var reproduceProbability: Double,
    var maxNumberOfOffspring: Int,
    var reproduceRange: Double,
    var reproduceMultiplyEnergy: Double,
    var reproduceAddEnergy: Double,
    var maxNumberOfSpecies: Int,

    // guiParameter: GuiParameter
    var size: Double

)
