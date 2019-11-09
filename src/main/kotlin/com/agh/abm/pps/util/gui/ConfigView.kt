package com.agh.abm.pps.util.gui

import com.agh.abm.pps.strategy.energy_transfer.EnergyTransferStrategyType
import com.agh.abm.pps.strategy.movement.MovementStrategyType
import com.agh.abm.pps.strategy.reproduce.ReproduceStrategyType
import com.agh.abm.pps.util.factory.SpeciesFactory
import javafx.scene.control.ComboBox
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import tornadofx.*
import kotlin.random.Random

class ConfigView : View() {

    val species = listOf(
        SpeciesConfData.fromSpecies(SpeciesFactory.standardGrass(Random))
        , SpeciesConfData.fromSpecies(SpeciesFactory.standardPredator(Random))
        , SpeciesConfData.fromSpecies(SpeciesFactory.standardPrey(Random))
    ).observable()

    private var movementStrategyCombo: ComboBox<MovementStrategyType> by singleAssign()
    private var energyTransferStrategyCombo: ComboBox<EnergyTransferStrategyType> by singleAssign()
    private var reproduceStrategyCombo: ComboBox<ReproduceStrategyType> by singleAssign()

    private var minEnergyField: TextField by singleAssign()
    private var maxEnergyField: TextField by singleAssign()
    private var energyField: TextField by singleAssign()
    private var maxConsumptionField: TextField by singleAssign()
    private var sizeField: TextField by singleAssign()
    private var reproduceRangeField: TextField by singleAssign()
    private var maxNumOfOffField: TextField by singleAssign()
    private var reproduceProbabilityField: TextField by singleAssign()
    private var reproduceCostField: TextField by singleAssign()
    private var reproduceThresholdField: TextField by singleAssign()
    private var moveMaxDistanceField: TextField by singleAssign()
    private var consumeRangeField: TextField by singleAssign()
    private var moveCostField: TextField by singleAssign()
    private var energyConsumeField: TextField by singleAssign()

    private var prevSelection: SpeciesConfData? = null

    private var tableViewField: TableView<SpeciesConfData> by singleAssign()

    override val root = borderpane {
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
                }
                fieldset("Others") {
                    field("Size") {
                        sizeField = textfield { }
                    }
//                    button("Save").action {
//                    }
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
            sizeField.textProperty().unbindBidirectional(sizeProperty)
        }

        movementStrategyCombo.bind(it.movementStrategyProperty)
        reproduceStrategyCombo.bind(it.reproduceStrategyProperty)
        energyTransferStrategyCombo.bind(it.energyTransferStrategyProperty)
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
        sizeField.bind(it.sizeProperty)

        prevSelection = it
    }
}
