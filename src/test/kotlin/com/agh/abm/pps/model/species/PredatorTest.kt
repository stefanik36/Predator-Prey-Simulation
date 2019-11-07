package com.agh.abm.pps.model.species

import com.agh.abm.pps.movement.RandomMovementStrategy
import com.agh.abm.pps.util.factory.AreaFactory
import com.agh.abm.pps.util.factory.SpeciesFactory
import org.junit.jupiter.api.Test

import com.agh.abm.pps.util.geometric.Vector
import org.junit.jupiter.api.Assertions
import kotlin.random.Random

class PredatorTest {

    @Test
    fun getOverview() {
        //init
        var predator = SpeciesFactory.standardPredator(Vector(4.0, 7.0))

        // run test
        var result = predator.getOverview()

        // check result
        Assertions.assertEquals("PREDATOR [4.00;7.00]; energy[10.00]", result)
    }


    @Test
    fun getOverview02() {
        //init
        var predator = SpeciesFactory.standardPredator(Vector(2.0, 3.0))

        // run test
        var result = predator.getOverview();

        // check result
        Assertions.assertEquals("PREDATOR [2.00;3.00]; energy[10.00]", result)
    }

    @Test
    fun move01() {
        //init
        var predator = SpeciesFactory.standardPredator(Vector(1.0, 2.0), RandomMovementStrategy(Random(2)))

        // run test
        predator.move()

        // check result
        Assertions.assertNotEquals(1.0, predator.currentPosition.x, 0.001)
        Assertions.assertNotEquals(2.0, predator.currentPosition.x, 0.001)
    }

    @Test
    fun eat01() {
        //init
        var predator = SpeciesFactory.standardPredator(Vector(1.0, 2.0), RandomMovementStrategy(Random(2)))
        var area = AreaFactory.standard3x3()

        // run test
        predator.eat(area)


    }




}
