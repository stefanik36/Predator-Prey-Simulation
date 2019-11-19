package com.agh.abm.pps.model.species

import com.agh.abm.pps.model.board.Area
import com.agh.abm.pps.model.parameter.*
import com.agh.abm.pps.strategy.die_strategy.DieStrategy
import com.agh.abm.pps.strategy.energy_transfer.EnergyTransferStrategy
import com.agh.abm.pps.util.geometric.Vector
import com.agh.abm.pps.strategy.movement.MovementStrategy
import com.agh.abm.pps.strategy.reproduce.ReproduceStrategy
import kotlin.math.max
import kotlin.math.min

abstract class Species(
    val movementStrategy: MovementStrategy,
    val energyTransferStrategy: EnergyTransferStrategy,
    val reproduceStrategy: ReproduceStrategy,
    val dieStrategy: DieStrategy,

    val consumeParameter: ConsumeParameter,
    val energyTransferParameter: EnergyTransferParameter,
    val movementParameter: MovementParameter,
    val reproduceParameter: ReproduceParameter,

    val guiParameter: GuiParameter
) {

    abstract fun getType(): SpeciesType

    fun move() {
        val nextPosition = movementStrategy.getNextPosition(movementParameter)
        val consumedEnergy = movementStrategy.getConsumedEnergy(
            movementParameter,
            movementParameter.currentPosition.distance(nextPosition)
        )
        movementParameter.currentPosition = nextPosition
        energyTransferParameter.energy -= consumedEnergy
    }

    open fun consume(area: Area) {
        val food = area.species
            .filter { s -> s.energyTransferParameter.alive }
            .filter { s -> this.canEat(s) }
            .filter { s -> this.isInConsumeRange(s) }

        val transferredEnergy = energyTransferStrategy.transfer(food, consumeParameter)
        energyTransferParameter.energy =
            min(energyTransferParameter.energy + transferredEnergy, energyTransferParameter.maxEnergy)

//        food.map { s -> s.getOverview() }.also { s -> println(s) }
    }

    fun performOtherActions(area: Area) {
        val shouldDie = dieStrategy.checkIfShouldDie(energyTransferParameter)
        if (shouldDie) {
            die()
            return
        }
        reproduce(area)
        energyTransferParameter.energy -= consumeParameter.restEnergyConsumption
    }

    fun reproduce(area: Area) {
        if (energyTransferParameter.energy >= reproduceParameter.reproduceThreshold) {
            val (newSpecies, cost) = reproduceStrategy.reproduce(this)
            area.add(newSpecies)
            energyTransferParameter.energy -= cost;
        }
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

    abstract fun generate(currentPosition: Vector, inEnergy: Double): Species

    private fun canEat(species: Species): Boolean =
        species.getType() in this.consumeParameter.canConsume

    private fun isInConsumeRange(species: Species): Boolean =
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
