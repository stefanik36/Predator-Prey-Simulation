package com.agh.abm.pps.gui

import com.agh.abm.pps.model.species.*
import com.agh.abm.pps.strategy.energy_transfer.EnergyTransferStrategyType
import com.agh.abm.pps.strategy.movement.MovementStrategyType
import com.agh.abm.pps.strategy.reproduce.ReproduceStrategyType
import com.agh.abm.pps.util.geometric.Vector
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty

import tornadofx.getValue
import tornadofx.setValue

@JsonIgnoreProperties(ignoreUnknown = true)
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
    @JsonIgnore
    val movementStrategyProperty = SimpleObjectProperty(this, "movementStrategy", movementStrategy)
    var movementStrategy: MovementStrategyType by movementStrategyProperty

    @JsonIgnore
    val energyTransferStrategyProperty = SimpleObjectProperty(this, "energyTransferStrategy", energyTransferStrategy)
    var energyTransferStrategy: EnergyTransferStrategyType by energyTransferStrategyProperty

    @JsonIgnore
    val reproduceStrategyProperty = SimpleObjectProperty(this, "reproduceStrategy", reproduceStrategy)
    var reproduceStrategy: ReproduceStrategyType by reproduceStrategyProperty

    @JsonIgnore
    val minEnergyProperty = SimpleDoubleProperty(this, "minEnergy", minEnergy)
    var minEnergy: Double by minEnergyProperty

    @JsonIgnore
    val maxEnergyProperty = SimpleDoubleProperty(this, "maxEnergy", maxEnergy)
    var maxEnergy: Double by maxEnergyProperty

    @JsonIgnore
    val energyProperty = SimpleDoubleProperty(this, "energy", inEnergy)
    var energy: Double by energyProperty

    @JsonIgnore
    val maxConsumptionProperty = SimpleDoubleProperty(this, "maxConsumption", maxConsumption)
    var maxConsumption: Double by maxConsumptionProperty

    @JsonIgnore
    val restEnergyConsumptionProperty = SimpleDoubleProperty(this, "restEnergyConsumption", restEnergyConsumption)
    var restEnergyConsumption: Double by restEnergyConsumptionProperty

    @JsonIgnore
    val consumeRangeProperty = SimpleDoubleProperty(this, "consumeRangeProperty", consumeRange)
    var consumeRange: Double by consumeRangeProperty

    @JsonIgnore
    val moveCostProperty = SimpleDoubleProperty(this, "moveCost", moveCost)
    var moveCost: Double by moveCostProperty

    @JsonIgnore
    val moveMaxDistanceProperty = SimpleDoubleProperty(this, "moveMaxDistance", moveMaxDistance)
    var moveMaxDistance: Double by moveMaxDistanceProperty

    @JsonIgnore
    val reproduceThresholdProperty = SimpleDoubleProperty(this, "reproduceThreshold", reproduceThreshold)
    var reproduceThreshold: Double by reproduceThresholdProperty

    @JsonIgnore
    val reproduceCostProperty = SimpleDoubleProperty(this, "reproduceCost", reproduceCost)
    var reproduceCost: Double by reproduceCostProperty

    @JsonIgnore
    val reproduceProbabilityProperty = SimpleDoubleProperty(this, "reproduceProbability", reproduceProbability)
    var reproduceProbability: Double by reproduceProbabilityProperty


    @JsonIgnore
    val maxNumberOfOffspringProperty = SimpleIntegerProperty(this, "maxNumberOfOffspring", maxNumberOfOffspring)
    var maxNumberOfOffspring: Int by maxNumberOfOffspringProperty

    @JsonIgnore
    val reproduceRangeProperty = SimpleDoubleProperty(this, "reproduceRange", reproduceRange)
    var reproduceRange: Double by reproduceRangeProperty

    @JsonIgnore
    val sizeProperty = SimpleDoubleProperty(this, "size", size)
    var size: Double by sizeProperty

    fun createSpecies(pos: Vector): Species {
        return when (type) {
            SpeciesType.GRASS -> Grass(
                pos,

                movementStrategy.strategy,
                energyTransferStrategy.strategy,
                reproduceStrategy.strategy,

                true,

                minEnergy,
                maxEnergy,
                energy,

                maxConsumption,
                restEnergyConsumption,
                consumeRange,
                listOf(),

                moveCost,
                moveMaxDistance,

                reproduceThreshold,
                reproduceCost,
                reproduceProbability,
                maxNumberOfOffspring,
                reproduceRange,
                1.0,
                0.0,

                size
            )
            SpeciesType.PREDATOR ->
                Predator(
                    pos,

                    movementStrategy.strategy,
                    energyTransferStrategy.strategy,
                    reproduceStrategy.strategy,

                    true,

                    minEnergy,
                    maxEnergy,
                    energy,

                    maxConsumption,
                    restEnergyConsumption,
                    consumeRange,
                    listOf(SpeciesType.PREY),

                    moveCost,
                    moveMaxDistance,
                    reproduceThreshold,
                    reproduceCost,
                    reproduceProbability,
                    maxNumberOfOffspring,
                    reproduceRange,
                    1.0,
                    0.0,

                    size
                )
            SpeciesType.PREY -> Prey(
                pos,
                movementStrategy.strategy,
                energyTransferStrategy.strategy,
                reproduceStrategy.strategy,

                true,

                minEnergy,
                maxEnergy,
                energy,

                maxConsumption,
                restEnergyConsumption,
                consumeRange,
                listOf(SpeciesType.GRASS),

                moveCost,
                moveMaxDistance,

                reproduceThreshold,
                reproduceCost,
                reproduceProbability,
                maxNumberOfOffspring,
                reproduceRange,
                1.0,
                0.0,

                size
            )
        }
    }

    fun toJson(): String {
        return ObjectMapper().writeValueAsString(this)
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

        fun fromJson(json: String): SpeciesConfData {
            return ObjectMapper().readValue(json, SpeciesConfData::class.java)
        }

    }
}



