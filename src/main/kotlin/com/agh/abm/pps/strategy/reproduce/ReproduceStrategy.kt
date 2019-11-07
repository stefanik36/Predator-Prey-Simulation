package com.agh.abm.pps.strategy.reproduce

import com.agh.abm.pps.model.species.Species


interface ReproduceStrategy {
    fun reproduce(species: Species): Pair<List<Species>, Double>
}