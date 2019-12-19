package com.agh.abm.pps.gui.data

import com.agh.abm.pps.model.board.Area
import com.agh.abm.pps.model.parameter.*
import com.agh.abm.pps.model.species.Species
import com.agh.abm.pps.strategy.die_strategy.DieStrategyType
import com.agh.abm.pps.strategy.energy_transfer.EnergyTransferStrategyType
import com.agh.abm.pps.strategy.movement.MovementStrategyType
import com.agh.abm.pps.strategy.reproduce.ReproduceStrategyType
import com.agh.abm.pps.util.geometric.Vector
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper
import javafx.beans.property.*
import javafx.scene.paint.Color
import tornadofx.getValue
import tornadofx.observable
import tornadofx.setValue

@JsonIgnoreProperties(ignoreUnknown = true)
class SpeciesConfData(
    movementStrategy: MovementStrategyType,
    energyTransferStrategy: EnergyTransferStrategyType,
    reproduceStrategy: ReproduceStrategyType,
//    TODO make dieStrategy more flexible
    dieStrategy: DieStrategyType,
    minEnergy: Double,
    maxEnergy: Double,
    inEnergy: Double,
    maxConsumption: Double,
    restEnergyConsumption: Double,
    consumeRange: Double,
    canConsume: MutableList<String>,
    moveCost: Double,
    moveMaxDistance: Double,
    reproduceThreshold: Double,
    reproduceCost: Double,
    reproduceProbability: Double,
    maxNumberOfOffspring: Int,
    reproduceRange: Double,
    reproduceMultiplyEnergy: Double,
    reproduceAddEnergy: Double,
    reproduceDensityLimit: Int,
    maxNumberOfSpecies: Int,
    size: Double,
    color: Color,
    type: String
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
    val dieStrategyProperty = SimpleObjectProperty(this, "dieStrategy", dieStrategy)
    var dieStrategy: DieStrategyType by dieStrategyProperty

    @JsonIgnore
    val minEnergyProperty = SimpleDoubleProperty(this, "minEnergy", minEnergy)
    var minEnergy: Double by minEnergyProperty

    @JsonIgnore
    val maxEnergyProperty = SimpleDoubleProperty(this, "maxEnergy", maxEnergy)
    var maxEnergy: Double by maxEnergyProperty

    @JsonIgnore
    val energyProperty = SimpleDoubleProperty(this, "energy", inEnergy)
    var energy: Double by energyProperty

    var alive: Boolean = true

    @JsonIgnore
    val maxConsumptionProperty = SimpleDoubleProperty(this, "maxConsumption", maxConsumption)
    var maxConsumption: Double by maxConsumptionProperty

    @JsonIgnore
    val restEnergyConsumptionProperty = SimpleDoubleProperty(this, "restEnergyConsumption", restEnergyConsumption)
    var restEnergyConsumption: Double by restEnergyConsumptionProperty

    @JsonIgnore
    val consumeRangeProperty = SimpleDoubleProperty(this, "consumeRange", consumeRange)
    var consumeRange: Double by consumeRangeProperty

    @JsonIgnore
    val canConsumeProperty = SimpleListProperty<String>(this, "canConsume", canConsume.observable())
    val canConsume: MutableList<String> by canConsumeProperty


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
    val reproduceDensityLimitProperty = SimpleIntegerProperty(this, "reproduceDensityLimit", reproduceDensityLimit)
    var reproduceDensityLimit: Int by reproduceDensityLimitProperty


    @JsonIgnore
    val maxNumberOfOffspringProperty = SimpleIntegerProperty(this, "maxNumberOfOffspring", maxNumberOfOffspring)
    var maxNumberOfOffspring: Int by maxNumberOfOffspringProperty

    @JsonIgnore
    val reproduceRangeProperty = SimpleDoubleProperty(this, "reproduceRange", reproduceRange)
    var reproduceRange: Double by reproduceRangeProperty

    @JsonIgnore
    val reproduceMultiplyEnergyProperty = SimpleDoubleProperty(this, "reproduceMultiplyEnergy", reproduceMultiplyEnergy)
    var reproduceMultiplyEnergy: Double by reproduceMultiplyEnergyProperty

    @JsonIgnore
    val reproduceAddEnergyProperty = SimpleDoubleProperty(this, "reproduceAddEnergy", reproduceAddEnergy)
    var reproduceAddEnergy: Double by reproduceAddEnergyProperty


    @JsonIgnore
    val maxNumberOfSpeciesProperty = SimpleIntegerProperty(this, "maxNumberOfSpecies", maxNumberOfSpecies)
    var maxNumberOfSpecies: Int by maxNumberOfSpeciesProperty

    @JsonIgnore
    val typeProperty = SimpleStringProperty(this, "type", type)
    var type: String by typeProperty


    @JsonIgnore
    val colorProperty = SimpleObjectProperty(this, "color", color)
    var color: Color by colorProperty


    @JsonIgnore
    val sizeProperty = SimpleDoubleProperty(this, "size", size)
    var size: Double by sizeProperty

    fun createSpecies(area: Area, pos: Vector): Species {
//        val color = area.speciesTypes[type]?.color ?: throw UnsupportedOperationException() //TODO parametrize in gui

        return Species(
            speciesName = type,
            movementStrategy = movementStrategy.strategy,
            energyTransferStrategy = energyTransferStrategy.strategy,
            reproduceStrategy = reproduceStrategy.strategy,
            dieStrategies = listOf(dieStrategy.strategy),
            consumeParameter = ConsumeParameter(
                maxConsumption,
                restEnergyConsumption,
                consumeRange,
                canConsume
            ),
            energyTransferParameter = EnergyTransferParameter(
                minEnergy,
                maxEnergy,
                energy,
                alive
            ),
            movementParameter = MovementParameter(pos, moveCost, moveMaxDistance),
            reproduceParameter = ReproduceParameter(
                reproduceThreshold,
                reproduceCost,
                reproduceProbability,
                maxNumberOfOffspring,
                reproduceRange,
                reproduceMultiplyEnergy,
                reproduceAddEnergy,
                maxNumberOfSpecies,
                reproduceDensityLimit
            ),
            guiParameter = GuiParameter(
                size = size,
                color = color
            )
        )
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
                , dieStrategy = speciesParameter.dieStrategies.first().getType()
                , minEnergy = speciesParameter.minEnergy
                , maxEnergy = speciesParameter.maxEnergy
                , inEnergy = speciesParameter.energy
                , maxConsumption = speciesParameter.maxConsumption
                , restEnergyConsumption = speciesParameter.restEnergyConsumption
                , consumeRange = speciesParameter.consumeRange
                , canConsume = speciesParameter.canConsume
                , moveCost = speciesParameter.moveCost
                , moveMaxDistance = speciesParameter.moveMaxDistance
                , reproduceThreshold = speciesParameter.reproduceThreshold
                , reproduceCost = speciesParameter.reproduceCost
                , reproduceProbability = speciesParameter.reproduceProbability
                , reproduceDensityLimit = speciesParameter.reproduceDensityLimit
                , maxNumberOfOffspring = speciesParameter.maxNumberOfOffspring
                , reproduceRange = speciesParameter.reproduceRange
                , reproduceMultiplyEnergy = speciesParameter.reproduceMultiplyEnergy
                , reproduceAddEnergy = speciesParameter.reproduceAddEnergy
                , maxNumberOfSpecies = speciesParameter.maxNumberOfSpecies
                , size = speciesParameter.size
                , color = speciesParameter.color
                , type = speciesParameter.type
            )
        }

        @JvmStatic
        fun fromSpecies(o: Species): SpeciesConfData {
            return SpeciesConfData(
                movementStrategy = o.movementStrategy.getType()
                , energyTransferStrategy = o.energyTransferStrategy.getType()
                , reproduceStrategy = o.reproduceStrategy.getType()
                , dieStrategy = o.dieStrategies.first().getType()
                , minEnergy = o.energyTransferParameter.minEnergy
                , maxEnergy = o.energyTransferParameter.maxEnergy
                , inEnergy = o.energyTransferParameter.energy
                , maxConsumption = o.consumeParameter.maxConsumption
                , restEnergyConsumption = o.consumeParameter.restEnergyConsumption
                , consumeRange = o.consumeParameter.consumeRange
                , canConsume = o.consumeParameter.canConsume
                , moveCost = o.movementParameter.moveCost
                , moveMaxDistance = o.movementParameter.moveMaxDistance
                , reproduceThreshold = o.reproduceParameter.reproduceThreshold
                , reproduceCost = o.reproduceParameter.reproduceCost
                , reproduceProbability = o.reproduceParameter.reproduceProbability
                , reproduceDensityLimit = o.reproduceParameter.reproduceDensityLimit
                , maxNumberOfOffspring = o.reproduceParameter.maxNumberOfOffspring
                , reproduceRange = o.reproduceParameter.reproduceRange
                , reproduceMultiplyEnergy = o.reproduceParameter.reproduceMultiplyEnergy
                , reproduceAddEnergy = o.reproduceParameter.reproduceAddEnergy
                , maxNumberOfSpecies = o.reproduceParameter.maxNumberOfSpecies
                , size = o.guiParameter.size
                , color = o.guiParameter.color
                , type = o.getType()
            )
        }

        fun fromJson(json: String): SpeciesConfData {
            return ObjectMapper().readValue(json, SpeciesConfData::class.java)
        }


    }
}



