package com.agh.abm.pps.util.factory

import com.agh.abm.pps.model.Area
import com.agh.abm.pps.strategy.movement.RandomMovementStrategy
import com.agh.abm.pps.util.geometric.Vector
import kotlin.random.Random

class AreaFactory {

    companion object {

        fun standard3x3x3(random: Random): Area {
            var predator01 = SpeciesFactory.standardPredator(random)
            var predator02 = SpeciesFactory.standardPredator(random)
            var predator03 = SpeciesFactory.standardPredator(random)

            var prey01 = SpeciesFactory.standardPrey(random)
            var prey02 = SpeciesFactory.standardPrey(random)
            var prey03 = SpeciesFactory.standardPrey(random)

            var grass01 = SpeciesFactory.standardGrass(random)
            var grass02 = SpeciesFactory.standardGrass(random)
            var grass03 = SpeciesFactory.standardGrass(random)

            return Area(
                mutableListOf(
                    predator01, predator02, predator03,
                    prey01, prey02, prey03,
                    grass01, grass02, grass03
                )
            )
        }

        fun standard3x3x3(): Area {
            var predator01 = SpeciesFactory.standardPredator(
                Vector(4.0, 7.0),
                Random(666)
            )
            var predator02 = SpeciesFactory.standardPredator(
                Vector(8.0, 2.0),
                Random(666)
            )
            var predator03 = SpeciesFactory.standardPredator(
                Vector(0.0, 3.0),
                Random(666)
            )

            var prey01 = SpeciesFactory.standardPrey(
                Vector(5.0, 6.0),
                Random(666)
            )
            var prey02 = SpeciesFactory.standardPrey(
                Vector(0.0, 1.0),
                Random(666)
            )
            var prey03 = SpeciesFactory.standardPrey(
                Vector(5.0, 0.0),
                Random(666)
            )

            var grass01 = SpeciesFactory.standardGrass(
                Vector(4.0, 2.0),
                Random(666)
            )
            var grass02 = SpeciesFactory.standardGrass(
                Vector(5.0, 0.0),
                Random(666)
            )
            var grass03 = SpeciesFactory.standardGrass(
                Vector(6.0, 6.0),
                Random(666)
            )

            return Area(
                mutableListOf(
                    predator01, predator02, predator03,
                    prey01, prey02, prey03,
                    grass01, grass02, grass03
                )
            )
        }

    }
}