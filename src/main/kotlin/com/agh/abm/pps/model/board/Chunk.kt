package com.agh.abm.pps.model.board

import com.agh.abm.pps.model.species.Species
import com.agh.abm.pps.model.species.SpeciesType
import com.agh.abm.pps.util.geometric.Vector
import kotlin.math.ceil
import kotlin.math.floor


class ChunkManager(
    width: Double,
    height: Double,
    private val chunkWidth: Double,
    private val chunkHeight: Double
) {

    private val chunks: Array<Chunk>
    private val chunksInWidth = width / chunkWidth
    private val chunksInHeight = height / chunkHeight

    init {
        val size = (chunksInHeight * chunksInWidth).toInt()
        chunks =
            Array(size) {
                val x = it % chunksInWidth
                val y = floor(it / chunksInWidth)
                Chunk(Vector(x * chunkWidth, y * chunkHeight), chunkWidth, chunkHeight, it, this)
            }
    }

    fun addSpecies(s: Species) {
        val pos = s.movementParameter.currentPosition
        val x = floor(pos.x / chunkWidth)
        val y = floor(pos.y / chunkHeight)
        val index = (y * chunksInWidth + x).toInt()
        if (index < chunks.size - 1 && index > 0) {
            chunks[index].species.add(s)
            s.chunk = chunks[index]
            chunks[index].countSpecies(s.getType())
        }
    }

    fun getOffsetNeighbor(current: Int, xOffset: Int, yOffset: Int): Chunk? {
        val index = current + xOffset * chunksInWidth.toInt() + yOffset
        if (index < 0 || index > chunks.size - 1) return null
        return chunks[index]
    }

    fun clear() {
        for (c in chunks) {
            c.species.clear()
            c.speciesNumber.clear()
        }
    }


}

class Chunk(
    private val pos: Vector,
    private val width: Double,
    private val height: Double,
    private val i: Int,
    private val manager: ChunkManager
) {
    val species: MutableList<Species> = mutableListOf()
    val speciesNumber: MutableMap<SpeciesType, Int> = mutableMapOf()

    fun ensureRange(p: Vector, range: Double): List<Species> {
        val species = mutableListOf<Species>()
        species.addAll(this.species)
        val top = ceil((range - p.y + pos.y) / height).toInt()
        val bottom = ceil((range + p.y - pos.y - height) / height).toInt()
        val left = ceil((range - p.x + pos.x) / width).toInt()
        val right = ceil((range + p.x - pos.x - width) / width).toInt()

        for (i in -left..right) {
            for (j in -top..bottom) {
                manager.getOffsetNeighbor(this.i, i, j)?.let { species.addAll(it.species) }
            }
        }
        return species
    }

    fun getNumber(t: SpeciesType): Int {
        return speciesNumber[t] ?: 100
    }

    fun countSpecies(t: SpeciesType) {
        if (speciesNumber.containsKey(t)) {
            speciesNumber[t] = speciesNumber[t]!! + 1
        } else {
            speciesNumber[t] = 1
        }
    }

}


