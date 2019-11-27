package com.agh.abm.pps.util.geometric

import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min

data class PositionRestriction(
    val minX: Double,
    val maxX: Double,
    val minY: Double,
    val maxY: Double
) {
    fun restrictX(x: Double): Double {
        return min(max(x, minX), maxX)
    }

    fun restrictY(y: Double): Double {
        return min(max(y, minY), maxY)
    }
}
