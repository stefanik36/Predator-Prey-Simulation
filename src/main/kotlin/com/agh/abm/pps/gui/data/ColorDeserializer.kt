package com.agh.abm.pps.gui.data

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.TreeNode
import com.fasterxml.jackson.databind.*
import javafx.scene.paint.Color
import com.fasterxml.jackson.databind.node.NumericNode





class ColorDeserializer : JsonDeserializer<Color>() {
    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): Color {
        val node: TreeNode = p!!.readValueAsTree()
        val red = node.get("red") as NumericNode
        val green = node.get("green") as NumericNode
        val blue = node.get("blue") as NumericNode
        val opacity = node.get("opacity") as NumericNode
//        val hue = node.get("hue") as NumericNode

        return Color.color(red.doubleValue(), green.doubleValue(), blue.doubleValue(), opacity.doubleValue())
    }
}

class ColorSerializer: JsonSerializer<Color>(){
    override fun serialize(value: Color?, gen: JsonGenerator?, serializers: SerializerProvider?) {
        gen?.run{
            writeStartObject()
            writeNumberField("red", value!!.red)
            writeNumberField("green", value.green)
            writeNumberField("blue", value.blue)
            writeNumberField("opacity", value.opacity)
            writeEndObject()
        }
    }

}
