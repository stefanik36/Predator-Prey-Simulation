import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class VectorTest {

    @Test
    fun of01() {
        var result = Vector.of(0.0 * 2 * Math.PI, 1.0)

        assertEquals(1.0, result.x, 0.001)
        assertEquals(0.0, result.y, 0.001)
    }

    @Test
    fun of02() {
        var result = Vector.of(0.5 * 2 * Math.PI, 2.0)

        assertEquals(-2.0, result.x, 0.001)
        assertEquals(0.0, result.y, 0.001)
    }

    @Test
    fun of03() {
        var result = Vector.of(0.25 * 2 * Math.PI, 0.5)

        assertEquals(0.0, result.x, 0.001)
        assertEquals(0.5, result.y, 0.001)
    }

    @Test
    fun of04() {
        var result = Vector.of(0.75 * 2 * Math.PI, 0.5)

        assertEquals(0.0, result.x, 0.001)
        assertEquals(-0.5, result.y, 0.001)
    }


    @Test
    fun add() {
        var vector01 = Vector(1.0, 3.7)
        var vector02 = Vector(1.6, 1.2)


        var result = vector01.add(vector02)

        assertEquals(2.6, result.x)
        assertEquals(4.9, result.y)

    }
}