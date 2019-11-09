package com.agh.abm.pps.util.gui

import com.agh.abm.pps.model.species.*
import com.agh.abm.pps.strategy.energy_transfer.EnergyTransferStrategy
import com.agh.abm.pps.strategy.energy_transfer.EnergyTransferStrategyType
import com.agh.abm.pps.strategy.movement.MovementStrategy
import com.agh.abm.pps.strategy.movement.MovementStrategyType
import com.agh.abm.pps.strategy.reproduce.ReproduceStrategy
import com.agh.abm.pps.strategy.reproduce.ReproduceStrategyType
import com.agh.abm.pps.util.geometric.Vector
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty

import tornadofx.getValue
import tornadofx.setValue

class SpeciesConfData(
    movementStrategy: MovementStrategyType,
    energyTransferStrategy: EnergyTransferStrategyType,
    reproduceStrategy: ReproduceStrategyType,
    minEnergy: Double,
    maxEnergy: Double,
    inEnergy: Double,
    maxConsumption: Double,
    restEnergyConsumption: Double,
    consumeRange: Double,
    moveCost: Double,
    moveMaxDistance: Double,
    reproduceThreshold: Double,
    reproduceCost: Double,
    reproduceProbability: Double,
    maxNumberOfOffspring: Int,
    reproduceRange: Double,
    size: Double,
    var type: SpeciesType
) {

    val movementStrategyProperty = SimpleObjectProperty(this, "movementStrategy", movementStrategy)
    var movementStrategy: MovementStrategyType by movementStrategyProperty

    val energyTransferStrategyProperty = SimpleObjectProperty(this, "energyTransferStrategy", energyTransferStrategy)
    var energyTransferStrategy: EnergyTransferStrategyType by energyTransferStrategyProperty

    val reproduceStrategyProperty = SimpleObjectProperty(this, "reproduceStrategy", reproduceStrategy)
    var reproduceStrategy: ReproduceStrategyType by reproduceStrategyProperty

    val minEnergyProperty = SimpleDoubleProperty(this, "minEnergy", minEnergy)
    var minEnergy: Double by minEnergyProperty

    val maxEnergyProperty = SimpleDoubleProperty(this, "maxEnergy", maxEnergy)
    var maxEnergy: Double by maxEnergyProperty

    val energyProperty = SimpleDoubleProperty(this, "energy", inEnergy)
    var energy: Double by energyProperty

    val maxConsumptionProperty = SimpleDoubleProperty(this, "maxConsumption", maxConsumption)
    var maxConsumption: Double by maxConsumptionProperty

    val restEnergyConsumptionProperty = SimpleDoubleProperty(this, "restEnergyConsumption", restEnergyConsumption)
    var restEnergyConsumption: Double by restEnergyConsumptionProperty

    val consumeRangeProperty = SimpleDoubleProperty(this, "consumeRangeProperty", consumeRange)
    var consumeRange: Double by consumeRangeProperty

    val moveCostProperty = SimpleDoubleProperty(this, "moveCost", moveCost)
    var moveCost: Double by moveCostProperty

    val moveMaxDistanceProperty = SimpleDoubleProperty(this, "moveMaxDistance", moveMaxDistance)
    var moveMaxDistance: Double by moveMaxDistanceProperty

    val reproduceThresholdProperty = SimpleDoubleProperty(this, "reproduceThreshold", reproduceThreshold)
    var reproduceThreshold: Double by reproduceThresholdProperty

    val reproduceCostProperty = SimpleDoubleProperty(this, "reproduceCost", reproduceCost)
    var reproduceCost: Double by reproduceCostProperty

    val reproduceProbabilityProperty = SimpleDoubleProperty(this, "reproduceProbability", reproduceProbability)
    var reproduceProbability: Double by reproduceProbabilityProperty

    val maxNumberOfOffspringProperty = SimpleIntegerProperty(this, "maxNumberOfOffspring", maxNumberOfOffspring)
    var maxNumberOfOffspring: Int by maxNumberOfOffspringProperty

    val reproduceRangeProperty = SimpleDoubleProperty(this, "reproduceRange", reproduceRange)
    var reproduceRange: Double by reproduceRangeProperty

    val sizeProperty = SimpleDoubleProperty(this, "size", size)
    var size: Double by sizeProperty

    fun createSpecies(pos: Vector): Species {
        return when (type) {
            SpeciesType.GRASS -> Grass(
                pos,
                movementStrategy.strategy,
                energyTransferStrategy.strategy,
                reproduceStrategy.strategy,
                listOf(),
                true,
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
            SpeciesType.PREDATOR ->
                Predator(
                    pos,
                    movementStrategy.strategy,
                    energyTransferStrategy.strategy,
                    reproduceStrategy.strategy,
                    listOf(SpeciesType.PREY),
                    true,
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
            SpeciesType.PREY -> Prey(
                pos,
                movementStrategy.strategy,
                energyTransferStrategy.strategy,
                reproduceStrategy.strategy,
                listOf(SpeciesType.GRASS),
                true,
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
    }

    companion object {
        @JvmStatic
        fun fromSpecies(o: Species): SpeciesConfData {
            return SpeciesConfData(
                movementStrategy = o.movementStrategy.getType()
                , energyTransferStrategy = o.energyTransferStrategy.getType()
                , reproduceStrategy = o.reproduceStrategy.getType()
                , minEnergy = o.minEnergy
                , maxEnergy = o.maxEnergy
                , inEnergy = o.energy
                , maxConsumption = o.maxConsumption
                , restEnergyConsumption = o.restEnergyConsumption
                , consumeRange = o.consumeRange
                , moveCost = o.moveCost
                , moveMaxDistance = o.moveMaxDistance
                , reproduceThreshold = o.reproduceThreshold
                , reproduceCost = o.reproduceCost
                , reproduceProbability = o.reproduceProbability
                , maxNumberOfOffspring = o.maxNumberOfOffspring
                , reproduceRange = o.reproduceRange
                , size = o.size
                , type = o.getType()
            )
        }
    }
}



