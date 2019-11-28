package com.agh.abm.pps.gui.gesture

import com.agh.abm.pps.gui.layout.PannableCanvas
import javafx.event.EventHandler
import javafx.scene.input.MouseEvent
import javafx.scene.input.ScrollEvent

internal class SceneGestures(var canvas: PannableCanvas) {

    private val sceneDragContext = DragContext()

    // right mouse button => panning
    val onMousePressedEventHandler: EventHandler<MouseEvent> =
        EventHandler { event ->
            if (!event.isSecondaryButtonDown)
                return@EventHandler

            sceneDragContext.mouseAnchorX = event.sceneX
            sceneDragContext.mouseAnchorY = event.sceneY

            sceneDragContext.translateAnchorX = canvas.translateX
            sceneDragContext.translateAnchorY = canvas.translateY
        }

    // right mouse button => panning
    val onMouseDraggedEventHandler: EventHandler<MouseEvent> =
        EventHandler { event ->
            if (!event.isSecondaryButtonDown)
                return@EventHandler

            canvas.translateX = sceneDragContext.translateAnchorX + event.sceneX - sceneDragContext.mouseAnchorX
            canvas.translateY = sceneDragContext.translateAnchorY + event.sceneY - sceneDragContext.mouseAnchorY

            event.consume()
        }

    /**
     * Mouse wheel handler: zoom to pivot point
     */
    // currently we only use Y, same value is used for X
    // note: pivot value must be untransformed, i. e. without scaling
    val onScrollEventHandler: EventHandler<ScrollEvent> = EventHandler { event ->
        val delta = 1.2

        var scale = canvas.scale
        val oldScale = scale

        if (event.deltaY < 0)
            scale /= delta
        else
            scale *= delta

        scale = clamp(
            scale,
            MIN_SCALE,
            MAX_SCALE
        )

        val f = scale / oldScale - 1

        val dx = event.sceneX - (canvas.boundsInParent.width / 2 + canvas.boundsInParent.minX)
        val dy = event.sceneY - (canvas.boundsInParent.height / 2 + canvas.boundsInParent.minY)

        canvas.scale = scale
        canvas.setPivot(f * dx, f * dy)
        println("scale: $scale")

        event.consume()
    }

    companion object {

        private const val MAX_SCALE = 10.0
        private const val MIN_SCALE = .1


        fun clamp(value: Double, min: Double, max: Double): Double {

            if (value.compareTo(min) < 0)
                return min

            return if (value.compareTo(max) > 0) max else value

        }
    }
}