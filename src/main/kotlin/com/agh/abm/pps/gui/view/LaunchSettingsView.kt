package com.agh.abm.pps.gui.view

import javafx.geometry.Pos
import javafx.scene.control.TextField
import tornadofx.*

class LaunchSettingsView : View() {

    private var widthText: TextField by singleAssign()
    private var heightText: TextField by singleAssign()

    override val root = borderpane {
        center {
            form {
                fieldset("Board") {
                    field("Width") {
                        widthText = textfield { text = "1000" }
                    }
                    field("Height") {
                        heightText = textfield { text = "1000" }
                    }
                }
                hbox {
                    spacing = 10.0
                    button("Launch") {}
                    button("Cancel") {}
                }
            }
        }
    }
}