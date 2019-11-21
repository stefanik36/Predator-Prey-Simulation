package com.agh.abm.pps.gui

import com.agh.abm.pps.model.parameter.*
import com.agh.abm.pps.model.species.*
import com.agh.abm.pps.strategy.die_strategy.TooLowEnergyDieStrategy
import com.agh.abm.pps.strategy.energy_transfer.EnergyTransferStrategyType
import com.agh.abm.pps.strategy.movement.MovementStrategyType
import com.agh.abm.pps.strategy.reproduce.ReproduceStrategyType
import com.agh.abm.pps.util.default_species.DefaultSpecies
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
                movementStrategy = movementStrategy.strategy,
                energyTransferStrategy = energyTransferStrategy.strategy,
                reproduceStrategy = reproduceStrategy.strategy,
                dieStrategy = TooLowEnergyDieStrategy(),
                consumeParameter = ConsumeParameter(
                    maxConsumption,
                    restEnergyConsumption,
                    consumeRange,
                    DefaultSpecies.grassParameters.canConsume//TODO parametrize from GUI
                ),
                energyTransferParameter = EnergyTransferParameter(
                    minEnergy,
                    maxEnergy,
                    energy,
                    DefaultSpecies.grassParameters.alive
                ),
                movementParameter = MovementParameter(pos, moveCost, moveMaxDistance),
                reproduceParameter = ReproduceParameter(
                    reproduceThreshold,
                    reproduceCost,
                    reproduceProbability,
                    maxNumberOfOffspring,
                    reproduceRange,
                    DefaultSpecies.grassParameters.reproduceMultiplyEnergy,//TODO parametrize from GUI
                    DefaultSpecies.grassParameters.reproduceAddEnergy,//TODO parametrize from GUI
                    DefaultSpecies.grassParameters.maxNumberOfSpecies//TODO parametrize from GUI
                ),
                guiParameter = GuiParameter(size)
            )
            SpeciesType.PREY -> Prey(
                movementStrategy = movementStrategy.strategy,
                energyTransferStrategy = energyTransferStrategy.strategy,
                reproduceStrategy = reproduceStrategy.strategy,
                dieStrategy = TooLowEnergyDieStrategy(),
                consumeParameter = ConsumeParameter(
                    maxConsumption,
                    restEnergyConsumption,
                    consumeRange,
                    DefaultSpecies.preyParameters.canConsume//TODO parametrize from GUI
                ),
                energyTransferParameter = EnergyTransferParameter(
                    minEnergy, maxEnergy, energy, DefaultSpecies.predatorParameters.alive
                ),
                movementParameter = MovementParameter(pos, moveCost, moveMaxDistance),
                reproduceParameter = ReproduceParameter(
                    reproduceThreshold,
                    reproduceCost,
                    reproduceProbability,
                    maxNumberOfOffspring,
                    reproduceRange,
                    reproduceMultiplyEnergy = DefaultSpecies.preyParameters.reproduceMultiplyEnergy,//TODO parametrize from GUI
                    reproduceAddEnergy = DefaultSpecies.preyParameters.reproduceAddEnergy,//TODO parametrize from GUI
                    maxNumberOfSpecies = DefaultSpecies.preyParameters.maxNumberOfSpecies//TODO parametrize from GUI
                ),
                guiParameter = GuiParameter(size)
            )
            SpeciesType.PREDATOR ->
                Predator(
                    movementStrategy = movementStrategy.strategy,
                    energyTransferStrategy = energyTransferStrategy.strategy,
                    reproduceStrategy = reproduceStrategy.strategy,
                    dieStrategy = TooLowEnergyDieStrategy(),
                    consumeParameter = ConsumeParameter(
                        maxConsumption,
                        restEnergyConsumption,
                        consumeRange,
                        DefaultSpecies.predatorParameters.canConsume//TODO parametrize from GUI
                    ),
                    energyTransferParameter = EnergyTransferParameter(
                        minEnergy, maxEnergy, energy, DefaultSpecies.predatorParameters.alive
                    ),
                    movementParameter = MovementParameter(pos, moveCost, moveMaxDistance),
                    reproduceParameter = ReproduceParameter(
                        reproduceThreshold,
                        reproduceCost,
                        reproduceProbability,
                        maxNumberOfOffspring,
                        reproduceRange,
                        DefaultSpecies.predatorParameters.reproduceMultiplyEnergy,//TODO parametrize from GUI
                        DefaultSpecies.predatorParameters.reproduceAddEnergy,//TODO parametrize from GUI
                        DefaultSpecies.predatorParameters.maxNumberOfSpecies//TODO parametrize from GUI
                    ),
                    guiParameter = GuiParameter(size)
                )
        }
    }

    fun toJson(): String {
        return ObjectMapper().writeValueAsString(this)
    }

    companion object {

        fun fromParameters(speciesParameter: SpeciesParameter): SpeciesConfData {
            return SpeciesConfData(
                movementStrategy = speciesParameter.movementStrategy.getType()
                , energyTransferStrategy = speciesParameter.energyTransferStrategy.getType()
                , reproduceStrategy = speciesParameter.reproduceStrategy.getType()
                , minEnergy = speciesParameter.minEnergy
                , maxEnergy = speciesParameter.maxEnergy
                , inEnergy = speciesParameter.energy
                , maxConsumption = speciesParameter.maxConsumption
                , restEnergyConsumption = speciesParameter.restEnergyConsumption
                , consumeRange = speciesParameter.consumeRange
                , moveCost = speciesParameter.moveCost
                , moveMaxDistance = speciesParameter.moveMaxDistance
                , reproduceThreshold = speciesParameter.reproduceThreshold
                , reproduceCost = speciesParameter.reproduceCost
                , reproduceProbability = speciesParameter.reproduceProbability
                , maxNumberOfOffspring = speciesParameter.maxNumberOfOffspring
                , reproduceRange = speciesParameter.reproduceRange
                , size = speciesParameter.size
                , type = speciesParameter.type
            )
        }

        @JvmStatic
        fun fromSpecies(o: Species): SpeciesConfData {
            return SpeciesConfData(
                movementStrategy = o.movementStrategy.getType()
                , energyTransferStrategy = o.energyTransferStrategy.getType()
                , reproduceStrategy = o.reproduceStrategy.getType()
                , minEnergy = o.energyTransferParameter.minEnergy
                , maxEnergy = o.energyTransferParameter.maxEnergy
                , inEnergy = o.energyTransferParameter.energy
                , maxConsumption = o.consumeParameter.maxConsumption
                , restEnergyConsumption = o.consumeParameter.restEnergyConsumption
                , consumeRange = o.consumeParameter.consumeRange
                , moveCost = o.movementParameter.moveCost
                , moveMaxDistance = o.movementParameter.moveMaxDistance
                , reproduceThreshold = o.reproduceParameter.reproduceThreshold
                , reproduceCost = o.reproduceParameter.reproduceCost
                , reproduceProbability = o.reproduceParameter.reproduceProbability
                , maxNumberOfOffspring = o.reproduceParameter.maxNumberOfOffspring
                , reproduceRange = o.reproduceParameter.reproduceRange
                , size = o.guiParameter.size
                , type = o.getType()
            )
        }

        fun fromJson(json: String): SpeciesConfData {
            return ObjectMapper().readValue(json, SpeciesConfData::class.java)
        }


    }
}



