package com.agh.abm.pps.model.parameter

import com.agh.abm.pps.model.species.SpeciesType
import com.agh.abm.pps.util.geometric.Vector

data class MovementParameter(
    var currentPosition: Vector,
    var moveCost: Double,
    var moveMaxDistance: Double
) {
}
