package com.agh.abm.pps.gui.view

import com.agh.abm.pps.gui.NOTIFY_POPULATION_GRAPH
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import tornadofx.*

class PopulationGraphView : View() {

    var preySeries: XYChart.Series<String, Number> by singleAssign()
    var predatorSeries: XYChart.Series<String, Number> by singleAssign()
    var grassSeries: XYChart.Series<String, Number> by singleAssign()
    var posi = 0

    override val root = group {
        linechart("Population graph", CategoryAxis(), NumberAxis()) {
            animated = false
            createSymbols = false
            predatorSeries = series("Alive predators")
            preySeries = series("Alive prays")
//            grassSeries = series("Alive grass")
        }
        button("Clear") {
            action {
                preySeries.data.clear()
                predatorSeries.data.clear()
//                grassSeries.data.clear()
                posi = 0
            }
        }
    }

    init {
        subscribe<NOTIFY_POPULATION_GRAPH> {
            if(posi > 300){
               preySeries.data.remove(0, 1)
                predatorSeries.data.remove(0, 1)
//                grassSeries.data.remove(0, 1)
            }

            preySeries.data.add(XYChart.Data(posi.toString(), it.alivePreyNum))
            predatorSeries.data.add(XYChart.Data(posi.toString(), it.alivePredNum))
//            grassSeries.data.add(XYChart.Data(posi.toString(), it.aliveGrassNum/10))
            posi++
        }
    }

}