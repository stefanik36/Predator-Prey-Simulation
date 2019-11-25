package com.agh.abm.pps.model.species

import com.agh.abm.pps.model.parameter.*
import com.agh.abm.pps.strategy.die_strategy.DieStrategy
import com.agh.abm.pps.strategy.energy_transfer.EnergyTransferStrategy
import com.agh.abm.pps.strategy.movement.MovementStrategy
import com.agh.abm.pps.strategy.reproduce.ReproduceStrategy
import com.agh.abm.pps.util.geometric.Vector

class Predator(
    movementStrategy: MovementStrategy,
    energyTransferStrategy: EnergyTransferStrategy,
    reproduceStrategy: ReproduceStrategy,
    dieStrategies: List<DieStrategy>,
    consumeParameter: ConsumeParameter,
    energyTransferParameter: EnergyTransferParameter,
    movementParameter: MovementParameter,
    reproduceParameter: ReproduceParameter,
    guiParameter: GuiParameter
) : Species(
    movementStrategy,
    energyTransferStrategy,
    reproduceStrategy,
    dieStrategies,
    consumeParameter,
    energyTransferParameter,
    movementParameter,
    reproduceParameter,
    guiParameter
) {
    private val speciesType: SpeciesType = SpeciesType.PREDATOR

    override fun getType(): SpeciesType {
        return speciesType;
    }

    override fun generate(currentPosition: Vector, inEnergy: Double): Species {
        return Predator(
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
}

