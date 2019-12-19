package com.agh.abm.pps.gui.view

import com.agh.abm.pps.gui.NOTIFY_POPULATION_GRAPH_MAP
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.LineChart
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import tornadofx.*
import kotlin.math.log2

class PopulationGraphView : View() {

    private var posi = 0
    private val limit = 300
    private var isLogScale = true


    private var chart: LineChart<String, Number> by singleAssign()
    private var seriesMap: MutableMap<String, XYChart.Series<String, Number>> = mutableMapOf()

    override val root = group {
        chart = linechart("Population graph", CategoryAxis(), NumberAxis()) {
            //            multiseries()
            animated = false
            createSymbols = false
        }
        hbox {

            button("Clear") {
                action {
                    chart.data.clear()
                    seriesMap.clear()
                    posi = 0
                }
            }

            button("Log scale on") {

                action {
                    isLogScale = !isLogScale
                    text = if (isLogScale) {
                        "Log scale on"
                    } else {
                        "Log scale off"
                    }
                }

            }
        }
    }

    init {
        subscribe<NOTIFY_POPULATION_GRAPH_MAP> {

            removePositionsOverLimit()

            for ((k, v) in it.alive) {
                seriesMap.getOrElse(k, {
                    val c = chart.series(k)
                    seriesMap[k] = c
                    c
                }).data.add(XYChart.Data(posi.toString(), withScale(v.toDouble())))
            }
            posi++
        }
    }

    private fun removePositionsOverLimit() {
        val diff = posi - limit
        for ((_, value) in seriesMap) {
            if (value.data.size > 0) {
                if (value.data[0].xValue.toInt() < diff) {
                    value.data.remove(0, 1)
                }
            }
        }
    }


    private fun withScale(v: Double): Double {
        return if (isLogScale) {
            log2(v) * 100
        } else {
            v
        }
    }


}
