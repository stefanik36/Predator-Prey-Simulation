package com.agh.abm.pps.gui.view

import com.agh.abm.pps.gui.data.SpeciesConfData
import com.agh.abm.pps.model.species.Species
import com.agh.abm.pps.model.species.SpeciesType
import com.agh.abm.pps.strategy.die_strategy.DieStrategyType
import com.agh.abm.pps.strategy.energy_transfer.EnergyTransferStrategyType
import com.agh.abm.pps.strategy.movement.MovementStrategyType
import com.agh.abm.pps.strategy.reproduce.ReproduceStrategyType
import com.agh.abm.pps.util.default_species.DefaultSpecies
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.collections.ObservableList
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.*
import tornadofx.*
import java.io.File
import java.io.FileWriter

class ConfigView : View() {

    private val filePath = "src/main/resources/models/JD"
    var species: ObservableList<SpeciesConfData>

    init {
        val file = File(filePath)
        species = if (file.exists()) {
            val mapper = ObjectMapper()
            mapper.registerModule(KotlinModule())
            (mapper.readValue(
                file,
                mapper.typeFactory.constructCollectionType(List::class.java, SpeciesConfData::class.java)
            ) as List<SpeciesConfData>).observable()
        } else {
            listOf(
                SpeciesConfData.fromParameters(DefaultSpecies.grassParameters)
                , SpeciesConfData.fromParameters(DefaultSpecies.preyParameters)
                , SpeciesConfData.fromParameters(DefaultSpecies.predatorParameters)
            ).observable()
        }
    }

    private var movementStrategyCombo: ComboBox<MovementStrategyType> by singleAssign()
    private var energyTransferStrategyCombo: ComboBox<EnergyTransferStrategyType> by singleAssign()
    private var reproduceStrategyCombo: ComboBox<ReproduceStrategyType> by singleAssign()
    private var dieStrategyCombo: ComboBox<DieStrategyType> by singleAssign()

    private var minEnergyField: TextField by singleAssign()
    private var maxEnergyField: TextField by singleAssign()
    private var energyField: TextField by singleAssign()
    private var maxConsumptionField: TextField by singleAssign()
    private var sizeField: TextField by singleAssign()
    private var reproduceRangeField: TextField by singleAssign()
    private var reproduceAddEnergyField: TextField by singleAssign()
    private var reproduceMultiplyEnergyField: TextField by singleAssign()
    private var reproduceMaxNumberOfSpeciesField: TextField by singleAssign()

    private var maxNumOfOffField: TextField by singleAssign()
    private var reproduceProbabilityField: TextField by singleAssign()
    private var reproduceCostField: TextField by singleAssign()
    private var reproduceThresholdField: TextField by singleAssign()
    private var moveMaxDistanceField: TextField by singleAssign()
    private var consumeRangeField: TextField by singleAssign()
    private var canConsumeListView: ListView<SpeciesType> by singleAssign()
    private var canConsumeSaveBtn: Button by singleAssign()
    private var moveCostField: TextField by singleAssign()
    private var energyConsumeField: TextField by singleAssign()

    private var prevSelection: SpeciesConfData? = null

    private var tableViewField: TableView<SpeciesConfData> by singleAssign()

    private var tList: ObservableList<SpeciesType> = species.map { it.type }.observable()

    override val root = borderpane {
        prefHeight = 700.0
        center {
            tableViewField = tableview(species) {
                column("Type", SpeciesConfData::type)
                column("Name", SpeciesConfData::type)
                selectionModel.selectedItemProperty().onChange {
                    editSpecies(it!!)
                }
            }
        }
        right {
            scrollpane {
                pannableProperty().set(true)
                fitToWidthProperty().set(true)
                paddingAll = 10.0
                form {
                    fieldset("Strategy:") {
                        field("Movement strategy") {
                            movementStrategyCombo = combobox(values = MovementStrategyType.values().toList()) { }
                        }
                        field("Energy transfer strategy") {
                            energyTransferStrategyCombo =
                                combobox(values = EnergyTransferStrategyType.values().toList()) { }
                        }
                        field("Reproduce strategy") {
                            reproduceStrategyCombo = combobox(values = ReproduceStrategyType.values().toList()) { }
                        }
                        field("Die strategy") {
                            dieStrategyCombo = combobox(values = DieStrategyType.values().toList()) {}
                        }
                    }
                    fieldset("Energy:") {
                        field("Min energy") {
                            minEnergyField = textfield {}
                        }
                        field("Max energy") {
                            maxEnergyField = textfield { }
                        }
                        field("Energy") {
                            energyField = textfield { }
                        }
                    }
                    fieldset("Consume:") {


                        field("Max consumption") {
                            maxConsumptionField = textfield { }
                        }
                        field("Energy consume") {
                            energyConsumeField = textfield { }
                        }
                        field("Consume range") {
                            consumeRangeField = textfield { }
                        }
                        field("Can consume") {
                            prefHeight = 100.0
                            canConsumeListView = listview(tList) {
                                //                                selectionModel
                                selectionModel.selectionMode = SelectionMode.MULTIPLE

                                prefWidth = 100.0
                                prefHeight = 100.0
                            }

                            canConsumeSaveBtn = button("Save") {
                            }

                        }
                    }
                    fieldset("Move:") {
                        field("Move cost") {
                            moveCostField = textfield { }
                        }
                        field("Move max distance") {
                            moveMaxDistanceField = textfield { }
                        }
                    }
                    fieldset("Reproduce:") {
                        field("Reproduce threshold") {
                            reproduceThresholdField = textfield { }
                        }
                        field("Reproduce cost") {
                            reproduceCostField = textfield { }
                        }
                        field("Reproduce probability") {
                            reproduceProbabilityField = textfield { }
                        }
                        field("Max number of offspring") {
                            maxNumOfOffField = textfield { }
                        }
                        field("Reproduce range") {
                            reproduceRangeField = textfield { }
                        }
                        field("Reproduce multiply energy") {
                            reproduceMultiplyEnergyField = textfield { }
                        }
                        field("Reproduce add species") {
                            reproduceAddEnergyField = textfield { }
                        }
                        field("Max number of species") {
                            reproduceMaxNumberOfSpeciesField = textfield { }
                        }
                    }
                    fieldset("Others") {
                        field("Size") {
                            sizeField = textfield { }
                        }
//                    button("Save").action {
//                        println(tList.toString())
//                    }
                    }
                }
            }
        }
    }

    init {
        tableViewField.selectionModel.selectFirst()
    }

    private fun editSpecies(it: SpeciesConfData) {

        prevSelection?.apply {
            movementStrategyProperty.unbindBidirectional(movementStrategyCombo.valueProperty())
            reproduceStrategyProperty.unbindBidirectional(reproduceStrategyCombo.valueProperty())
            energyTransferStrategyProperty.unbindBidirectional(energyTransferStrategyCombo.valueProperty())
            dieStrategyProperty.unbindBidirectional(dieStrategyCombo.valueProperty())
            minEnergyField.textProperty().unbindBidirectional(minEnergyProperty)
            maxEnergyField.textProperty().unbindBidirectional(maxEnergyProperty)
            energyField.textProperty().unbindBidirectional(energyProperty)
            maxConsumptionField.textProperty().unbindBidirectional(maxConsumptionProperty)
            energyConsumeField.textProperty().unbindBidirectional(restEnergyConsumptionProperty)
            consumeRangeField.textProperty().unbindBidirectional(consumeRangeProperty)
            moveCostField.textProperty().unbindBidirectional(moveCostProperty)
            moveMaxDistanceField.textProperty().unbindBidirectional(moveMaxDistanceProperty)
            reproduceThresholdField.textProperty().unbindBidirectional(reproduceThresholdProperty)
            reproduceCostField.textProperty().unbindBidirectional(reproduceCostProperty)
            reproduceProbabilityField.textProperty().unbindBidirectional(reproduceProbabilityProperty)
            maxNumOfOffField.textProperty().unbindBidirectional(maxNumberOfOffspringProperty)
            reproduceRangeField.textProperty().unbindBidirectional(reproduceRangeProperty)
            reproduceMultiplyEnergyField.textProperty().unbindBidirectional(reproduceMultiplyEnergyProperty)
            reproduceAddEnergyField.textProperty().unbindBidirectional(reproduceAddEnergyProperty)
            reproduceMaxNumberOfSpeciesField.textProperty().unbindBidirectional(maxNumberOfSpeciesProperty)
            sizeField.textProperty().unbindBidirectional(sizeProperty)
            canConsumeListView.selectionModel.clearSelection()
        }

        movementStrategyCombo.bind(it.movementStrategyProperty)
        reproduceStrategyCombo.bind(it.reproduceStrategyProperty)
        energyTransferStrategyCombo.bind(it.energyTransferStrategyProperty)
        dieStrategyCombo.bind(it.dieStrategyProperty)
        minEnergyField.bind(it.minEnergyProperty)
        maxEnergyField.bind(it.maxEnergyProperty)
        energyField.bind(it.energyProperty)
        maxConsumptionField.bind(it.maxConsumptionProperty)
        energyConsumeField.bind(it.restEnergyConsumptionProperty)
        consumeRangeField.bind(it.consumeRangeProperty)
        moveCostField.bind(it.moveCostProperty)
        moveMaxDistanceField.bind(it.moveMaxDistanceProperty)
        reproduceThresholdField.bind(it.reproduceThresholdProperty)
        reproduceCostField.bind(it.reproduceCostProperty)
        reproduceProbabilityField.bind(it.reproduceProbabilityProperty)
        maxNumOfOffField.bind(it.maxNumberOfOffspringProperty)
        reproduceRangeField.bind(it.reproduceRangeProperty)
        reproduceMultiplyEnergyField.bind(it.reproduceMultiplyEnergyProperty)
        reproduceAddEnergyField.bind(it.reproduceAddEnergyProperty)
        reproduceMaxNumberOfSpeciesField.bind(it.maxNumberOfSpeciesProperty)
        sizeField.bind(it.sizeProperty)
        canConsumeListView.selectWhere { x -> x in it.canConsume }
        canConsumeSaveBtn.action {
            it.canConsumeProperty.clear()
            it.canConsumeProperty.addAll(canConsumeListView.selectionModel.selectedItems)
        }

//        canConsumeListView.selectionModel.selectedItemProperty().addListener(ChangeListener<SpeciesType>{observable, oldVal, newVal ->
//            if(newVal == null){
//                it.canConsumeProperty.clear()
//            }
//            else{
//                if(newVal !in it.canConsumeProperty){
//                    it.canConsumeProperty.add(newVal)
//                }
//            }
//            println("$oldVal || $newVal")
//        })

        prevSelection = it
    }

    override fun onUndock() {
        val fw = FileWriter(File(filePath))
        fw.write(ObjectMapper().writeValueAsString(species.toList()))
        fw.close()
    }
}
