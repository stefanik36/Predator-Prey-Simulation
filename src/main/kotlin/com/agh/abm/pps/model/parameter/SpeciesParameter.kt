package com.agh.abm.pps.model.parameter

import com.agh.abm.pps.strategy.die_strategy.DieStrategy
import com.agh.abm.pps.strategy.energy_transfer.EnergyTransferStrategy
import com.agh.abm.pps.strategy.movement.MovementStrategy
import com.agh.abm.pps.strategy.reproduce.ReproduceStrategy
import com.agh.abm.pps.util.geometric.Vector
import javafx.scene.paint.Color

data class SpeciesParameter(
    var type: String,

    var movementStrategy: MovementStrategy,
    var energyTransferStrategy: EnergyTransferStrategy,
    var reproduceStrategy: ReproduceStrategy,
    var dieStrategies: List<DieStrategy>,

    // consumeParameter: ConsumeParameter
    var maxConsumption: Double,
    var restEnergyConsumption: Double,
    var consumeRange: Double,
    var canConsume: MutableList<String>,

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
    var reproduceDensityLimit: Int,

    // guiParameter: GuiParameter
    var size: Double,
    var color: Color

)
