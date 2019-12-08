package com.agh.abm.pps.model.species

import com.agh.abm.pps.model.board.Area
import com.agh.abm.pps.model.board.Chunk
import com.agh.abm.pps.model.parameter.*
import com.agh.abm.pps.strategy.die_strategy.DieStrategy
import com.agh.abm.pps.strategy.energy_transfer.EnergyTransferStrategy
import com.agh.abm.pps.strategy.movement.MovementStrategy
import com.agh.abm.pps.strategy.reproduce.ReproduceStrategy
import com.agh.abm.pps.util.geometric.Vector
import kotlin.math.max

class Species(
    val speciesName: String,

    val movementStrategy: MovementStrategy,
    val energyTransferStrategy: EnergyTransferStrategy,
    val reproduceStrategy: ReproduceStrategy,
    val dieStrategies: List<DieStrategy>,

    val consumeParameter: ConsumeParameter,
    val energyTransferParameter: EnergyTransferParameter,
    val movementParameter: MovementParameter,
    val reproduceParameter: ReproduceParameter,

    val guiParameter: GuiParameter
) {

    var chunk: Chunk? = null

    fun getType(): String {
        return speciesName
    }

    fun move(area: Area) {
        val nextPosition = movementStrategy.getNextPosition(movementParameter, area)
        val consumedEnergy = movementStrategy.getConsumedEnergy(
            movementParameter,
            movementParameter.currentPosition.distance(nextPosition)
        )
        movementParameter.currentPosition = nextPosition
        energyTransferParameter.energy -= consumedEnergy
    }

    fun consume() {
        val transferredEnergy = energyTransferStrategy.transfer(this)
        transferredEnergy?.let { energyTransferParameter.energy = energyTransferParameter.energy + it }
    }

    fun performDieActions() {
        val shouldDie = dieStrategies.any { ds -> ds.checkIfShouldDie(energyTransferParameter) }
        if (shouldDie) {
            die()
        }
    }

    fun reproduce(area: Area) {
        val offspringListAndCost = reproduceStrategy.reproduce(this, area)

        offspringListAndCost?.let {
            val (newSpecies, cost) = it
            area.add(newSpecies)
            energyTransferParameter.energy -= cost
        }
    }

    fun performOtherActions() {
        energyTransferParameter.energy -= consumeParameter.restEnergyConsumption
    }

    private fun die() {
        energyTransferParameter.alive = false
    }

    fun takeEnergy(amountOfEnergy: Double): Double {
        val remainingEnergy = max(energyTransferParameter.minEnergy, energyTransferParameter.energy - amountOfEnergy)
        val takenEnergy = energyTransferParameter.energy - remainingEnergy
        energyTransferParameter.energy = remainingEnergy
        return takenEnergy
    }

    fun generate(currentPosition: Vector, inEnergy: Double): Species {
        return Species(
            speciesName,
            movementStrategy,
            energyTransferStrategy,
            reproduceStrategy,
            dieStrategies,
            consumeParameter.copy(),
            energyTransferParameter.copy().also { it.energy = inEnergy },
            movementParameter.copy().also { it.currentPosition = currentPosition },
            reproduceParameter.copy(),
            guiParameter.copy()
        )
    }

    fun canConsume(species: Species): Boolean =
        species.getType() in this.consumeParameter.canConsume

    fun isInConsumeRange(species: Species): Boolean =
        species.movementParameter.currentPosition.distance(this.movementParameter.currentPosition) <= this.consumeParameter.consumeRange

    fun getOverview(): String {
        return (if (energyTransferParameter.alive) "" else "- ") +
                "${getType()} " +
                "[${String.format("%.2f", movementParameter.currentPosition.x)};${String.format(
                    "%.2f",
                    movementParameter.currentPosition.y
                )}]; " +
                "energy[${String.format("%.2f", energyTransferParameter.energy)}]"
    }


}
