package com.agh.abm.pps.util.geometric

import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class Vector(val x: Double, val y: Double) {

    fun add(shift: Vector): Vector {
        return Vector(this.x + shift.x, this.y + shift.y)
    }

    fun distance(vector: Vector): Double {
        return sqrt((this.x - vector.x).pow(2) + (this.y - vector.y).pow(2))
    }

    companion object {
        fun of(angle: Double, distance: Double): Vector {
            val x = distance * cos(angle)
            val y = distance * sin(angle)

            return Vector(x, y)
        }
    }


}