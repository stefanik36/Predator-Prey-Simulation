package com.agh.abm.pps
import kotlin.math.cos
import kotlin.math.sin

class Vector(val x: Double, val y: Double) {

    fun add(shift: Vector): Vector {
        return Vector(this.x + shift.x, this.y + shift.y)
    }

    companion object {
        fun of(angle: Double, distance: Double): Vector {

            val x = distance * cos(angle)
            val y = distance * sin(angle)

            return Vector(x, y)
        }
    }


}