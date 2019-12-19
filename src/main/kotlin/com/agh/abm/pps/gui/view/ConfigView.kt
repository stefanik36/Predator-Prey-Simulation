package com.agh.abm.pps.gui.view

import com.agh.abm.pps.SimulationController
import com.agh.abm.pps.gui.BoardState
import com.agh.abm.pps.gui.REFRESH_SELECTED_TYPE
import com.agh.abm.pps.gui.data.SpeciesConfData
import com.agh.abm.pps.strategy.die_strategy.DieStrategyType
import com.agh.abm.pps.strategy.energy_transfer.EnergyTransferStrategyType
import com.agh.abm.pps.strategy.movement.MovementStrategyType
import com.agh.abm.pps.strategy.reproduce.ReproduceStrategyType
import com.agh.abm.pps.util.default_species.DefaultSpecies
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import javafx.collections.ObservableList
import javafx.scene.control.*
import tornadofx.*
import java.io.File
import java.io.FileWriter

class ConfigView : View() {

    private val filePath = "src/main/resources/models/species"
    private val boardPath = "src/main/resources/models/board"
    var species: ObservableList<SpeciesConfData>

    var speciesTypes: ObservableList<String>

    private val controller: SimulationController by inject()

    init {
        val file = File(filePath)
        species = if (file.exists()) {
            val mapper = ObjectMapper()
            mapper.registerModule(KotlinModule())
            (mapper.readValue(
                file,
                mapper.typeFactory.constructCollectionType(List::class.java, SpeciesConfData::class.java)
            ) as MutableList<SpeciesConfData>).observable()
        } else {
            mutableListOf(
                SpeciesConfData.fromParameters(DefaultSpecies.grassParameters)
                , SpeciesConfData.fromParameters(DefaultSpecies.bushParameters)
                , SpeciesConfData.fromParameters(DefaultSpecies.preyParameters)
                , SpeciesConfData.fromParameters(DefaultSpecies.predatorParameters)
            ).observable()
        }

        speciesTypes = species.map { it.type }.observable()

        val bFile = File(boardPath)
        if (bFile.exists()) {
            val mapper = ObjectMapper()
            mapper.registerModule(KotlinModule())
            val nBoard = mapper.readValue(bFile, BoardState::class.java)
            controller.board.chunkSize = nBoard.chunkSize
            controller.board.width = nBoard.width
            controller.board.height = nBoard.height
            controller.reload()
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
    private var reproduceDensityLimitField: TextField by singleAssign()

    private var maxNumOfOffField: TextField by singleAssign()
    private var reproduceProbabilityField: TextField by singleAssign()
    private var reproduceCostField: TextField by singleAssign()
    private var reproduceThresholdField: TextField by singleAssign()
    private var moveMaxDistanceField: TextField by singleAssign()
    private var consumeRangeField: TextField by singleAssign()
    private var canConsumeListView: ListView<String> by singleAssign()
    private var canConsumeSaveBtn: Button by singleAssign()
    private var moveCostField: TextField by singleAssign()
    private var energyConsumeField: TextField by singleAssign()

    private var prevSelection: SpeciesConfData? = null

    private var tableViewField: TableView<SpeciesConfData> by singleAssign()


    private var boardWidthField: TextField by singleAssign()
    private var boardHeightField: TextField by singleAssign()
    private var chunkSizeField: TextField by singleAssign()
    private var saveToFileField: CheckBox by singleAssign()

    private var colorPickerField: ColorPicker by singleAssign()

    private var typeField: TextField by singleAssign()
    private var newItemTypeField: TextField by singleAssign()

    private val vHeight: Double = 700.0

    override val root = borderpane {
        prefHeight = vHeight
        center {

            vbox {

                tableViewField = tableview(species) {
                    prefHeight = vHeight - 20
                    column("Type", SpeciesConfData::type) {
                        prefWidth = 250.0
                    }
                    selectionModel.selectedItemProperty().onChange {
                        editSpecies(it!!)
                    }
                    selectionModel.selectedIndexProperty().onChange { fire(REFRESH_SELECTED_TYPE(it)) }
                }
                hbox {
                    newItemTypeField = textfield { }
                    spacing = 5.0
                    button("Add") {
                        action {
                            addNewSpecies()
                        }
                    }
                    button("Remove") {
                        action {
                            removeSelectedSpecies()
                        }
                    }
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
                            maxHeight = 400.0
                            canConsumeListView = listview(speciesTypes) {
                                //                                selectionModel
                                selectionModel.selectionMode = SelectionMode.MULTIPLE

                                prefWidth = 100.0
                                prefHeight = 300.0
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
                        field("Reproduce density limit") {
                            reproduceDensityLimitField = textfield { }
                        }
                        field("Max number of species") {
                            reproduceMaxNumberOfSpeciesField = textfield { }
                        }
                    }
                    fieldset("Others") {

                        field("Type") {
                            typeField = textfield {
                                textProperty().addListener { _, _, _ -> tableViewField.refresh() }
                            }
                        }

                        field("Size") {
                            sizeField = textfield { }
                        }
                        field("Color") {
                            colorPickerField = colorpicker { }
                        }
                    }
                    fieldset("Area") {
                        field("Area width") {
                            boardWidthField = textfield { text = controller.board.width.toString() }
                        }
                        field("Area height") {
                            boardHeightField = textfield { text = controller.board.height.toString() }
                        }
                        field("Chunk size") {
                            chunkSizeField = textfield { text = controller.board.chunkSize.toString() }
                        }
                        button("Reload area") {
                            action {
                                val b = controller.board
                                b.width = boardWidthField.text.toDouble()
                                b.height = boardHeightField.text.toDouble()
                                b.chunkSize = chunkSizeField.text.toDouble()
                                controller.reload()
                            }
                        }
                    }
                    fieldset("Save") {
                        saveToFileField = checkbox("Autosave") { selectedProperty().value = false }
                    }
                }
            }
        }
    }

    init {
        tableViewField.selectionModel.selectFirst()
    }

    fun force() {}

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
            reproduceDensityLimitField.textProperty().unbindBidirectional(reproduceDensityLimitProperty)
            reproduceMaxNumberOfSpeciesField.textProperty().unbindBidirectional(maxNumberOfSpeciesProperty)
            typeField.textProperty().unbindBidirectional(typeProperty)
            sizeField.textProperty().unbindBidirectional(sizeProperty)
            colorPickerField.valueProperty().unbindBidirectional(colorProperty)
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
        reproduceDensityLimitField.bind(it.reproduceDensityLimitProperty)
        reproduceMaxNumberOfSpeciesField.bind(it.maxNumberOfSpeciesProperty)
        typeField.bind(it.typeProperty)
        sizeField.bind(it.sizeProperty)
        colorPickerField.bind(it.colorProperty)
        canConsumeListView.selectWhere { x -> x in it.canConsume }
        canConsumeSaveBtn.action {
            it.canConsumeProperty.clear()
            it.canConsumeProperty.addAll(canConsumeListView.selectionModel.selectedItems)
        }
        prevSelection = it
    }


    private fun addNewSpecies() {
        val p = DefaultSpecies.defaultParameters
        p.type = newItemTypeField.text
        species.add(SpeciesConfData.fromParameters(p))
        speciesTypes.add(DefaultSpecies.defaultParameters.type)
    }

    private fun removeSelectedSpecies() {
        speciesTypes.remove(tableViewField.selectionModel.selectedItem.type)
        species.remove(tableViewField.selectionModel.selectedItem)
    }

    override fun onUndock() {
        if (!saveToFileField.isSelected) return

        val fw = FileWriter(File(filePath))
        fw.write(ObjectMapper().writeValueAsString(species.toList()))
        fw.close()

        val fwBoard = FileWriter(File(boardPath))
        fwBoard.write(ObjectMapper().writeValueAsString(controller.board))
        fwBoard.close()
    }
}
