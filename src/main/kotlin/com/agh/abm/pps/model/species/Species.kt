package com.agh.abm.pps.model.species

import com.agh.abm.pps.model.Area
import com.agh.abm.pps.strategy.energy_transfer.EnergyTransferStrategy
import com.agh.abm.pps.util.geometric.Vector
import com.agh.abm.pps.strategy.movement.MovementStrategy
import com.agh.abm.pps.strategy.reproduce.ReproduceStrategy
import kotlin.math.max
import kotlin.math.min

abstract class Species(
    var currentPosition: Vector,

    //strategies
    val movementStrategy: MovementStrategy,
    val energyTransferStrategy: EnergyTransferStrategy,
    val reproduceStrategy: ReproduceStrategy,

    var alive: Boolean,

    //energy
    val minEnergy: Double,
    val maxEnergy: Double,
    inEnergy: Double,

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
    var energy: Double = min(inEnergy, maxEnergy);
    abstract fun getType(): SpeciesType

    fun move() {
        val nextPosition = movementStrategy.getNextPosition(moveMaxDistance, currentPosition)
        val consumedEnergy = movementStrategy.getConsumedEnergy(currentPosition.distance(nextPosition), moveCost)
        currentPosition = nextPosition
        energy -= consumedEnergy
    }

    open fun consume(area: Area) {
        val food = area.species
            .filter { s -> s.alive }
            .filter { s -> this.canEat(s) }
            .filter { s -> this.isInRange(s) }

        val transferredEnergy = energyTransferStrategy.transfer(food, maxConsumption)
        energy = min(energy + transferredEnergy, maxEnergy)

//        food.map { s -> s.getOverview() }.also { s -> println(s) }
    }

    fun performOtherActions(area: Area) {
        if (energy <= minEnergy) {
            die()
            return
        }
        reproduce(area)
        energy -= restEnergyConsumption
    }

    fun reproduce(area: Area) {
        if (energy >= reproduceThreshold) {
            val (newSpecies, cost) = reproduceStrategy.reproduce(this, reproduceMultiplyEnergy, reproduceAddEnergy)
            area.add(newSpecies)
            energy -= cost;
        }
    }

    private fun die() {
        alive = false
    }

    fun takeEnergy(amountOfEnergy: Double): Double {
        val remainingEnergy = max(minEnergy, energy - amountOfEnergy)
        val takenEnergy = energy - remainingEnergy
        energy = remainingEnergy
        return takenEnergy
    }

    abstract fun generate(currentPosition: Vector, inEnergy: Double): Species

    private fun canEat(species: Species): Boolean =
        species.getType() in this.eats

    private fun isInRange(species: Species): Boolean =
        species.currentPosition.distance(this.currentPosition) <= this.consumeRange

    fun getOverview(): String {
        return (if (alive) "" else "- ") +
                "${getType()} " +
                "[${String.format("%.2f", currentPosition.x)};${String.format("%.2f", currentPosition.y)}]; " +
                "energy[${String.format("%.2f", energy)}]"
    }


}
