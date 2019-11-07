package com.agh.abm.pps.model.species

import com.agh.abm.pps.model.Area
import com.agh.abm.pps.movement.EnergyTransferStrategy
import com.agh.abm.pps.util.geometric.Vector
import com.agh.abm.pps.movement.MovementStrategy
import kotlin.math.max
import kotlin.math.min

abstract class Species(
    var currentPosition: Vector,

    protected val movementStrategy: MovementStrategy,
    protected val energyTransferStrategy: EnergyTransferStrategy,

    protected val eats: List<SpeciesType>,

    var alive: Boolean,

    //energy
    protected val minEnergy: Double,
    protected val maxEnergy: Double,

    var energy: Double,

    protected val maxTransfer: Double,
    protected val energyConsume: Double,

    //costs
    protected val moveCost: Double,

    protected val moveMaxDistance: Double,

    val size: Double,
    val range: Double
) {
    abstract fun getType(): SpeciesType

    fun move() {
        val nextPosition = movementStrategy.getNextPosition(moveMaxDistance, currentPosition)
        val consumedEnergy = movementStrategy.getConsumedEnergy(currentPosition.distance(nextPosition), moveCost)
        currentPosition = nextPosition
        energy -= consumedEnergy
    }

    fun eat(area: Area) {
        val food = area.species
            .filter { s -> s.alive }
            .filter { s -> this.canEat(s) }
            .filter { s -> this.isInRange(s) }

        val transferredEnergy = energyTransferStrategy.transfer(food, maxTransfer)
        energy = min(energy + transferredEnergy, maxEnergy)

//        food.map { s -> s.getOverview() }.also { s -> println(s) }
    }

    fun performOtherActions() {
        if (energy <= minEnergy) {
            die()
        }
        //TODO

        energy -= energyConsume
    }

    fun reproduce() {
        TODO()
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

    private fun canEat(species: Species): Boolean =
        species.getType() in this.eats

    private fun isInRange(species: Species): Boolean =
        species.currentPosition.distance(this.currentPosition) <= this.range

    fun getOverview(): String {
        return (if (alive) "" else "- ") +
                "${getType()} " +
                "[${String.format("%.2f", currentPosition.x)};${String.format("%.2f", currentPosition.y)}]; " +
                "energy[${String.format("%.2f", energy)}]"
    }


}