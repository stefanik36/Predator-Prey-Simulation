package com.agh.abm.pps.model.species

import com.agh.abm.pps.model.board.Area
import com.agh.abm.pps.model.board.Chunk
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
    val dieStrategies: List<DieStrategy>,

    val consumeParameter: ConsumeParameter,
    val energyTransferParameter: EnergyTransferParameter,
    val movementParameter: MovementParameter,
    val reproduceParameter: ReproduceParameter,

    val guiParameter: GuiParameter

) {

    var chunk: Chunk? = null

    abstract fun getType(): SpeciesType

    fun move(area: Area) {
        val nextPosition = movementStrategy.getNextPosition(movementParameter, area)
        val consumedEnergy = movementStrategy.getConsumedEnergy(
            movementParameter,
            movementParameter.currentPosition.distance(nextPosition)
        )
        movementParameter.currentPosition = nextPosition
        energyTransferParameter.energy -= consumedEnergy
    }

    open fun consume(area: Area) {
        if (chunk == null) return
        val food = chunk!!.ensureRange(movementParameter.currentPosition, consumeParameter.consumeRange)
            .asSequence()
            .filter { s -> s.energyTransferParameter.alive }
            .filter { s -> this.canEat(s) }
            .filter { s -> this.isInConsumeRange(s) }
            .toList()


        val transferredEnergy = energyTransferStrategy.transfer(food, consumeParameter)
        energyTransferParameter.energy =
            min(energyTransferParameter.energy + transferredEnergy, energyTransferParameter.maxEnergy)

    }

    fun performDieActions() {
        val shouldDie = dieStrategies.any { ds -> ds.checkIfShouldDie(energyTransferParameter) }
        if (shouldDie) {
            die()
            return
        }
    }

    fun reproduce(area: Area) {
        if (energyTransferParameter.energy >= reproduceParameter.reproduceThreshold) {
            val (newSpecies, cost) = reproduceStrategy.reproduce(this, area)
            area.add(newSpecies)
            energyTransferParameter.energy -= cost;
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
