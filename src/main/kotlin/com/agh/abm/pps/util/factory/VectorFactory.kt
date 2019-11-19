package com.agh.abm.pps.util.factory

import com.agh.abm.pps.util.geometric.Vector
import kotlin.random.Random

class VectorFactory {

    companion object {

        fun random(random: Random, range: Double): Vector {
            val angle = random.nextDouble(0.0, 2 * Math.PI)
            val distance = random.nextDouble(0.0, range)

            return Vector.of(angle, distance)
        }

        fun zero(): Vector {
            return Vector.of(0.0, 0.0)
        }

    }
}
