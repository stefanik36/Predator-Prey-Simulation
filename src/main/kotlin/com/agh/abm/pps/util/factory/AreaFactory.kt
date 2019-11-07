package com.agh.abm.pps.util.factory

import com.agh.abm.pps.model.Area
import com.agh.abm.pps.model.species.Predator
import com.agh.abm.pps.model.species.Prey
import com.agh.abm.pps.movement.MovementStrategy
import com.agh.abm.pps.movement.RandomMovementStrategy
import com.agh.abm.pps.util.geometric.Vector
import kotlin.random.Random

class AreaFactory {

    companion object {

        fun standard3x3(): Area {
            var predator01 = SpeciesFactory.standardPredator(Vector(4.0, 7.0), RandomMovementStrategy(Random(666)))
            var predator02 = SpeciesFactory.standardPredator(Vector(8.0, 2.0), RandomMovementStrategy(Random(666)))
            var predator03 = SpeciesFactory.standardPredator(Vector(0.0, 3.0), RandomMovementStrategy(Random(666)))

            var prey01 = SpeciesFactory.standardPrey(Vector(5.0, 6.0), RandomMovementStrategy(Random(666)))
            var prey02 = SpeciesFactory.standardPrey(Vector(0.0, 1.0), RandomMovementStrategy(Random(666)))
            var prey03 = SpeciesFactory.standardPrey(Vector(5.0, 0.0), RandomMovementStrategy(Random(666)))

            return Area(listOf(predator01, predator02, predator03, prey01, prey02, prey03))
        }

    }
}