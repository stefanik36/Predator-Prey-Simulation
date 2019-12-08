package com.agh.abm.pps.strategy.energy_transfer

import com.agh.abm.pps.model.species.Species

class GetFromAllEnergyTransferStrategy : EnergyTransferStrategy {

    override fun transfer(species: Species): Double? {
        val food = getFood(species)
        return food?.let { it.map { s -> s.takeEnergy(species.consumeParameter.maxConsumption / it.size) }.sum() }
    }

    private fun getFood(species: Species): List<Species>? {
        with(species) {
            if (consumeParameter.canConsume.isEmpty()) return null
            if (chunk == null) return null
            return chunk!!.ensureRange(movementParameter.currentPosition, consumeParameter.consumeRange)
                .asSequence()
                .filter { s -> s.energyTransferParameter.alive }
                .filter { s -> canConsume(s) }
                .filter { s -> isInConsumeRange(s) }
                .toList()
        }
    }

    override fun getType(): EnergyTransferStrategyType {
        return EnergyTransferStrategyType.GET_FROM_ALL
    }
}
