package species

import org.junit.jupiter.api.Test

import Vector
import org.junit.jupiter.api.Assertions

class PredatorTest {

    @Test
    fun getOverview() {
        //init
        var predator = Predator.standard(Vector(4.0, 7.0))

        // run test
        var result = predator.getOverview()

        // check result
        Assertions.assertEquals("Predator: [4.0;7.0]", result)
    }


    @Test
    fun getOverview02() {
        //init
        var predator = Predator.standard(Vector(2.0, 3.0))

        // run test
        var result = predator.getOverview();

        // check result
        Assertions.assertEquals("Predator: [2.0;3.0]", result)
    }

    @Test
    fun move01() {
        //init
        var predator = Predator.standard(Vector(1.0, 2.0))

        // run test
        predator.move()

        // check result
        Assertions.assertNotEquals(1.0, predator.currentPosition.x, 0.001)
        Assertions.assertNotEquals(2.0, predator.currentPosition.x, 0.001)
    }
}
