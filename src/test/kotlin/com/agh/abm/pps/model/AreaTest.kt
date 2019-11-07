package com.agh.abm.pps.model

import com.agh.abm.pps.strategy.movement.RandomMovementStrategy
import com.agh.abm.pps.util.factory.AreaFactory
import com.agh.abm.pps.util.factory.SpeciesFactory
import com.agh.abm.pps.util.geometric.Vector
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.random.Random

class AreaTest {
    @Test
    fun getOverview() {
        //init
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

        var area = Area(mutableListOf(predator01, predator02, predator03, prey01, prey02, prey03))


        area.nextStep();


        var result = area.getOverview();
        result.also(::println)


        Assertions.assertEquals(
            "AREA [1]:\n" +
                    "\tPREDATOR [4.93;7.38]; energy[11.00],\n" +
                    "\tPREDATOR [8.93;2.38]; energy[11.00],\n" +
                    "\tPREDATOR [0.93;3.38]; energy[11.00],\n" +
                    "\tPREY [5.93;6.38]; energy[5.00],\n" +
                    "\tPREY [0.93;1.38]; energy[5.00],\n" +
                    "\tPREY [5.93;0.38]; energy[5.00],\n" +
                    "\tPREY [-0.32;-0.41]; energy[1.00],\n" +
                    "\tPREY [-0.32;-0.41]; energy[1.00]", result
        )
    }


    @Test
    fun fewSteps() {
        //init
        var area = AreaFactory.standard3x3x3(Random(666))


        for (i in 1..40) {
            area.getOverview().also(::println)
            area.nextStep()
        }



        Assertions.assertEquals(40, area.step)
    }
}